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

import com.innoviti.emi.entity.core.ModelComposite;

public class ModelCompositeBridge implements TwoWayFieldBridge {

  private static final Logger logger = LoggerFactory.getLogger(ModelCompositeBridge.class);

  @Override
  public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
    ModelComposite modelComposite = (ModelComposite) value;
    Field field = new StringField(name + ".innoModelCode", modelComposite.getInnoModelCode(),
        luceneOptions.getStore());
    field.setBoost(luceneOptions.getBoost());
    document.add(field);
    field = new StringField(name + ".crtupdDate", String.valueOf(modelComposite.getCrtupdDate()),
        luceneOptions.getStore());
    field.setBoost(luceneOptions.getBoost());
    document.add(field);
  }

  @Override
  public Object get(String name, Document document) {
    ModelComposite modelComposite = new ModelComposite();
    IndexableField field = document.getField(name + ".innoModelCode");
    modelComposite.setInnoModelCode(field.stringValue());
    field = document.getField(name + ".crtupdDate");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      modelComposite.setCrtupdDate(sdf.parse(field.stringValue()));
    } catch (Exception e) {
      logger.error("Exception", e);
    }

    return modelComposite;
  }

  @Override
  public String objectToString(Object object) {
    ModelComposite id = (ModelComposite) object;
    return id.toString();
  }

}
