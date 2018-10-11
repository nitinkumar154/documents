package com.innoviti.emi.service.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.core.SequenceGenerator;
import com.innoviti.emi.repository.core.SequenceGeneratorRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.SequenceGeneratorService;

@Service
@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRES_NEW, isolation=Isolation.DEFAULT)
public class SequenceGeneratorServiceImpl extends CrudServiceHelper<SequenceGenerator, String>
    implements SequenceGeneratorService {

  private SequenceGeneratorRepository sequenceGeneratorRepository;
  
  public SequenceGeneratorServiceImpl(SequenceGeneratorRepository sequenceGeneratorRepository) {
    super(sequenceGeneratorRepository);
    this.sequenceGeneratorRepository = sequenceGeneratorRepository;
  }

  @Override
  public SequenceGenerator create(SequenceGenerator u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(String id) {
    helperDeleteById(id);
  }

  @Override
  public SequenceGenerator findById(String id) {
    return helperFindById(id);
  }

  @Override
  public SequenceGenerator update(SequenceGenerator u) {
    return helperUpdate(u);
  }

  @Override
  public List<SequenceGenerator> findAll() {
    return helperFindAll();
  }

  @Override
  public int updateSequenceGenerator(String seqName) {
    sequenceGeneratorRepository.flush();
    int rowEffected = sequenceGeneratorRepository.updateSequenceGenerator(seqName);
    return rowEffected;
  }

  @Override
  public long getLastInsertedId() {
    return sequenceGeneratorRepository.getLastInsertedId();
  }

  @Override
  public long getSeqNumber(String seqName) {
    updateSequenceGenerator(seqName);
    return getLastInsertedId();
  }

}
