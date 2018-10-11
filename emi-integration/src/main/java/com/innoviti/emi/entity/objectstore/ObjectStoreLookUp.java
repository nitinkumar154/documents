package com.innoviti.emi.entity.objectstore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "objectstore_lookup")
public class ObjectStoreLookUp implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 702967265244193151L;

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, unique = true)
  private int id;

  @Column(name = "file_key", nullable = false, unique = true)
  private String key;

  @Column(name = "md5", nullable = false, unique = true)
  private String md5;

  @Column(name = "file_name", nullable = false, unique = true)
  private String fileName;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "user_type")
  private String userType;

  @Column(name = "last_modified")
  private String lastModified;

  @Column(columnDefinition = "VARCHAR(1) default 'N'", name = "is_processed")
  private char isProcessed;

  @Column(name = "created_date", columnDefinition = "datetime", nullable = false)
  private Date createdDate;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getMd5() {
    return md5;
  }

  public void setMd5(String md5) {
    this.md5 = md5;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getLastModified() {
    return lastModified;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public char getIsProcessed() {
    return isProcessed;
  }

  public void setIsProcessed(char isProcessed) {
    this.isProcessed = isProcessed;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }



  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

}
