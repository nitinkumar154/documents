package com.innoviti.emi.repository.core.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.repository.core.ModelViewRepositoryCustom;

@Repository
@Transactional(transactionManager = "transactionManager")
public class ModelViewRepositoryImpl implements ModelViewRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public int createModelView() {
    Query query = entityManager.createNativeQuery("CREATE OR REPLACE " + "VIEW `model_name` AS "
        + "(SELECT md.model_code AS innoModelCode, CONCAT(model_display_number, ' - ', manufacturer_display_name) AS innoModelDisplayName "
        + "FROM `manufacturers` m "
        + "INNER JOIN models md ON m.manufacturer_code = md.manufacturer_code "
        + "WHERE md.model_code <> 'XXXXXXXXXXGEN' "
        + "GROUP BY md.model_code)");
    return query.executeUpdate();
  }

}
