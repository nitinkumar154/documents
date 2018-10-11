package com.innoviti.emi.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class IntegrationUtil {
  
  private static final Logger logger = Logger.getLogger(IntegrationUtil.class);

  public static byte[] getPayloadAsBytes(Message<?> message) {
    byte[] bytes = null;
    Object payload = message.getPayload();
    if (payload instanceof byte[]) {
        bytes = (byte[]) payload;
    }
    else if (payload instanceof String) {
        try {
            bytes = ((String) payload).getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new MessageHandlingException(message, e);
        }
    }
    else {
      logger.error("Exception in converting payload into bytes");
        throw new MessageHandlingException(message,
                "expects " +
                "either a byte array or String payload, but received: " + payload.getClass());
    }
    return bytes;
  }
  
  public static String uploadClient(File file, String fileType, String userType, String url) {
    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("fileType", fileType);
    map.add("userType", userType);
    map.add("file", new FileSystemResource(file));
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
        new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
    
    try {
      RestTemplate template = new RestTemplate();
      ResponseEntity<String> result = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
      logger.info("HTTP status code is : " + result.getStatusCode());
      
      if (result.getStatusCode() == HttpStatus.OK) {
        return result.getBody();
      }
    } catch (HttpClientErrorException e) {
        logger.error("HttpClientErrorException", e);
        logger.debug(e.getResponseBodyAsString());
    } catch (Exception e) {
        logger.error("Exception", e);
    }

    return null;
  }
  
  public static String uploadClient(byte[] bytes, String fileType, String userType, String url, String fileName) {
    
    ByteArrayResource resource = new ByteArrayResource(bytes) {
      @Override
      public String getFilename() {
          return fileName;
      }
    };
    
    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("fileType", fileType);
    map.add("userType", userType);
    map.add("file", resource);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
        new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
    
    try {
      RestTemplate template = new RestTemplate();
      ResponseEntity<String> result = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
      logger.info("HTTP status code is : " + result.getStatusCode());
      
      if (result.getStatusCode() == HttpStatus.OK) {
        return result.getBody();
      }
    } catch (HttpClientErrorException e) {
        logger.error("HttpClientErrorException", e);
        logger.debug(e.getResponseBodyAsString());
    } catch (Exception e) {
        logger.error("Exception", e);
    }

    return null;
  }
}
