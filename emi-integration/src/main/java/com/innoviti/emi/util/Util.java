package com.innoviti.emi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Util {
  
  private static Logger logger = LoggerFactory.getLogger(Util.class);
  
  public static final String TEMP_FOLDER= "temp";
  public static final String EMI_FOLDER = "mapping_files";
  public static final String RESULT_FILE_TYPE_1 = "job1_result";
  public static final String RESULT_FILE_TYPE_2 = "job2_result";
  
  private Util() {
    throw new AssertionError("Cannot be instantiated");
  }

  private static String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
  private static String datePattern2 = "\\d{4}-\\d{2}-\\d{1}";
  private static String datePattern3 = "\\d{4}-\\d{1}-\\d{2}";
  private static String datePattern4 = "\\d{2}-\\d{2}-\\d{4}";
  private static String datePattern5 = "\\d{2}-\\d{1}-\\d{4}";
  private static String datePattern6 = "\\d{2}/\\d{2}/\\d{4}";
  private static String datePattern7 = "\\d{4}/\\d{2}/\\d{2}";
  private static String datePattern8 = "\\d{2}/\\d{1}/\\d{4}";
  private static String datePattern9 = "\\d{4}/\\d{1}/\\d{2}";
 

  public static LocalDate dateToLocalDate(Date date) {
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = instant.atZone(zoneId);
    return zdt.toLocalDate();
  }

  public static Date localDateToDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static String currentDateTimeFormater(String dateFormat) {
    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    return today.format(dateTimeFormatter);
  }

  public static String formatDateToString(String dateFormat, Date date) {
    SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
    return dateFormatter.format(date);
  }

  public static ResponseEntity<Resource> downloadFileService(String url) {
    ResponseEntity<Resource> response = null;
    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
      HttpEntity<String> entity = new HttpEntity<String>(headers);

      response = restTemplate.exchange(url, HttpMethod.GET, entity, Resource.class);

    } catch (Exception e) {
    }
    return response;
  }

  public static String getRightPaddedValueFormatter(String value, int length) {
    if (value.length() > length)
      throw new IllegalArgumentException(" The field cannot be padded as size exceeds the limit.");
    return String.format("%-" + length + "s", value);
  }

  public static String getLeftPaddedValueFormatter(String value, int length) {
    if (value.length() > length)
      throw new IllegalArgumentException(" The field cannot be padded as size exceeds the limit.");
    return String.format("%" + length + "s", value);
  }

  public static String getLeftPaddedValueZeroFilled(String value, int length) {
    if (value.length() > length)
      throw new IllegalArgumentException(" The field cannot be padded as size exceeds the limit.");
    return String.format("%0" + length + "d", Integer.valueOf(value));
  }

  public static Date getDateFormat(String date) {
    Date modifiedDate = null;
    boolean result1 = date.matches(datePattern1);
    boolean result2 = date.matches(datePattern2);
    boolean result3 = date.matches(datePattern3);
    boolean result4 = date.matches(datePattern4);
    boolean result5 = date.matches(datePattern5);
    boolean result6 = date.matches(datePattern6);
    boolean result7 = date.matches(datePattern7);
    boolean result8 = date.matches(datePattern8);
    boolean result9 = date.matches(datePattern9);
    if (result1) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    } else if (result2) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    } else if (result3) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result4){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result5){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result6){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result7){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result8){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    else if(result9){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/dd");
      LocalDate dateTime = LocalDate.parse(date, formatter);
      modifiedDate = localDateToDate(dateTime);
    }
    if(modifiedDate == null){
      throw new IllegalArgumentException("Unparsable date : "+date);
    }
    return modifiedDate;
  }
  public static Path createDirectories(String folder){
    Path path = Paths.get(folder);
    if (!path.toFile().exists()) {
      try {
        path = Files.createDirectories(path);
      } catch (IOException e) {
        logger.error("Exception creating folder", e);
      }
    }
    return path;
  }
}
