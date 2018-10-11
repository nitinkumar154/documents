package com.innoviti.emi.repository.core;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.core.SequenceGenerator;
import com.innoviti.emi.repository.BaseRepository;

public interface SequenceGeneratorRepository extends BaseRepository<SequenceGenerator, String> {

  @Modifying(clearAutomatically = true)
  @Query(
      value = "UPDATE sequence_generator SET seq_value = LAST_INSERT_ID(seq_value + 1) WHERE seq_name = ?1",
      nativeQuery = true)
  int updateSequenceGenerator(String seqName);

  @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
  long getLastInsertedId();

}
