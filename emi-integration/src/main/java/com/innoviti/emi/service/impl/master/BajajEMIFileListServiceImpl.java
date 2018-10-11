package com.innoviti.emi.service.impl.master;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.exception.RequestNotCompletedException;
import com.innoviti.emi.repository.master.ObjectStoreLookUpRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.BajajEMIFileListService;
import com.innoviti.emi.util.Util;

@Service
@Transactional(transactionManager = "transactionManager")
public class BajajEMIFileListServiceImpl extends CrudServiceHelper<ObjectStoreLookUp, String>
    implements BajajEMIFileListService {

  public BajajEMIFileListServiceImpl(ObjectStoreLookUpRepository baseRespository) {
    super(baseRespository);
  }

  private static Logger logger = LoggerFactory.getLogger(BajajEMIFileListServiceImpl.class);


  @Autowired
  private ObjectStoreLookUpRepository objectRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${store.upload.url}")
  private String bajajEmiFile;

 
  @Override
  public List<ObjectStoreLookUp> getList() {
    logger.info("Entered in getList");
    String date = Util.currentDateTimeFormater("dd/MM/yyyy");
    return listOfFiles(date);
  }
  private void saveFile(List<ObjectStoreLookUp> storeFiles) {

    for (ObjectStoreLookUp object : storeFiles) {
      if(object.getFileType().equals("request")) {
        ObjectStoreLookUp obj = objectRepository.findByFileName(object.getFileName().substring(0, 4));
        object.setIsProcessed('N');
        object.setCreatedDate(new Date());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        long milliSeconds = Long.parseLong(object.getLastModified());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        object.setLastModified(formatter.format(calendar.getTime()));

        if (obj == null) {
          helperCreate(object);
        } else {
          object.setId(obj.getId());
          BeanUtils.copyProperties(object, obj);
          helperUpdate(obj);
        }
      }
    }
  }

  @Override
  public ObjectStoreLookUp getFileKey(String fileName) {
    logger.info("Entered in getFileKey");
    ObjectStoreLookUp fileKey = null;
    try {
      fileKey = objectRepository.findByFileNameAndIsProcessed(fileName);
    } catch (Exception e) {
      logger.error("Error in getFileKey", e);
      throw new RequestNotCompletedException("Error in getting File Key", 500);
    }
    logger.info("Exit from  getFileKey");
    return fileKey;
  }

  @Override
  public ObjectStoreLookUp create(ObjectStoreLookUp u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub

  }

  @Override
  public ObjectStoreLookUp findById(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ObjectStoreLookUp update(ObjectStoreLookUp u) {
    return helperUpdate(u);
  }

  @Override
  public List<ObjectStoreLookUp> findAll() {
    return objectRepository.findAll();
  }
  @Override
  public List<ObjectStoreLookUp> getList(String date) {
    return listOfFiles(date);
  }

  private List<ObjectStoreLookUp> listOfFiles(String date){
    List<ObjectStoreLookUp> myObjects = null;
    
    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
    params.add("userType", "bajaj");
    params.add("date", date);
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      HttpEntity<MultiValueMap<String, Object>> requestEntity =
          new HttpEntity<MultiValueMap<String, Object>>(params, headers);
      ResponseEntity<String> response = restTemplate.exchange(bajajEmiFile + "/last_modified",
          HttpMethod.POST, requestEntity, String.class);
      ObjectMapper mapper = new ObjectMapper();
      myObjects = mapper.readValue(response.getBody(),
          mapper.getTypeFactory().constructCollectionType(List.class, ObjectStoreLookUp.class));
      saveFile(myObjects);
    } catch (Exception e) {
      logger.error("Error in  getList", e);
      throw new RequestNotCompletedException("Error in getting Bajaj file", 500);
    }
    logger.info("Exit from getList");
    return myObjects;
  }
}
