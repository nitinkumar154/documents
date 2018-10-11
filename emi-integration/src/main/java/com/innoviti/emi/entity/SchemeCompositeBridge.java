package com.innoviti.emi.entity;

import java.text.SimpleDateFormat;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexableField;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innoviti.emi.entity.core.SchemeComposite;

public class SchemeCompositeBridge implements TwoWayFieldBridge{

  private static final Logger logger = LoggerFactory.getLogger(SchemeCompositeBridge.class);
  @Override
  public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
    SchemeComposite schemeComposite = (SchemeComposite) value;
    Field field = new StringField(name+".issuer_scheme_model_code", 
        schemeComposite.getInnoIssuerSchemeCode(), luceneOptions.getStore());
    field.setBoost(luceneOptions.getBoost());
    document.add(field);
    
    field = new StringField(name+".crtupdDate", String.valueOf(schemeComposite.getCrtupdDate()), 
        luceneOptions.getStore());
    field.setBoost(luceneOptions.getBoost());
    document.add(field);
  }

  @Override
  public Object get(String name, Document document) {
    SchemeComposite schemeComposite = new SchemeComposite();
    IndexableField field = document.getField(name+".issuer_scheme_model_code");
    schemeComposite.setInnoIssuerSchemeCode(field.stringValue());
    field = document.getField(name + ".crtupdDate");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      schemeComposite.setCrtupdDate(sdf.parse(field.stringValue()));
    } catch (Exception e) {
      logger.error("Exception", e);
    }
    return schemeComposite;
  }

  @Override
  public String objectToString(Object object) {
    SchemeComposite schemeComposite = (SchemeComposite) object;
    return schemeComposite.toString();
  }

}
