package com.innoviti.emi.integration;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
@IntegrationComponentScan
public class SftpOutboundAdapter {
  
  private static final Logger logger = Logger.getLogger(SftpOutboundAdapter.class);
  
  @Autowired
  SessionFactory<LsEntry> sftpSessionFactory;
  
  @Autowired
  private Environment env;
  
  @Bean
  @ServiceActivator(inputChannel = "toSftpChannel")
  public MessageHandler handler() {
    SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
    handler.setRemoteDirectoryExpression(new LiteralExpression(env.getRequiredProperty("sftp.remote.dir")+"/processed-files"));
    handler.setFileNameGenerator(new FileNameGenerator() {
      @Override
      public String generateFileName(Message<?> message) {
        if (message.getPayload() instanceof File) {
          return ((File) message.getPayload()).getName();
        } else {
          logger.error("File expected as payload. But received different object");
          throw new IllegalArgumentException("File expected as payload.");
        }
      }
    });
    return handler;
  }

  @MessagingGateway(name="UploadGateway")
  public interface UploadGateway {
    @Gateway(requestChannel = "toSftpChannel")
    void sendToFtp(File file);
  }

}
