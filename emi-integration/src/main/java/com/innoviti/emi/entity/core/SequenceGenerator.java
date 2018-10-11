package com.innoviti.emi.entity.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequence_generator")
public class SequenceGenerator implements Serializable {

  private static final long serialVersionUID = -6716271889327220415L;

  @Id
  @Column(name = "seq_name", nullable = false)
  private String seqName;

  @Column(name = "seq_value", nullable = false)
  private long value;

  public String getSeqName() {
    return seqName;
  }

  public void setSeqName(String seqName) {
    this.seqName = seqName;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(seqName);
    builder.append("|");
    builder.append(value);
    return builder.toString();
  }

}
