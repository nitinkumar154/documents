package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "data_move_keeper")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataMoveKeeper {

  @Id
  @Column(name = "table_name", length = 255, nullable = false)
  private String tableName;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "time_stamp", nullable = true)
  private Date timeStamp;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Override
  public String toString() {
    return "DataMovementKeeper [tableName=" + tableName + ", timeStamp=" + timeStamp + "]";
  }

}
