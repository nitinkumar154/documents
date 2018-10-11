package com.innoviti.emi.database.csv;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

public class HeaderWriter implements FlatFileHeaderCallback{
  private final String header;

  public HeaderWriter(String header) {
    this.header = header;
  }
  @Override
  public void writeHeader(Writer writer) throws IOException {
    writer.write(header);
  }

}
