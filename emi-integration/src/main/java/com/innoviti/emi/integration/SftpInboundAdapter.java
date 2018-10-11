package com.innoviti.emi.integration;

import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.integration.sftp.filters.SftpPersistentAcceptOnceFileListFilter;
import org.springframework.integration.sftp.filters.SftpRegexPatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpStreamingMessageSource;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.transformer.StreamTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.innoviti.emi.util.IntegrationUtil;
import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
public class SftpInboundAdapter {
  
  private static final Logger logger = Logger.getLogger(SftpInboundAdapter.class);
  
  @Autowired
  SessionFactory<LsEntry> sftpSessionFactory;
  
  @Autowired
  private Environment env;
  
  @Bean
  @InboundChannelAdapter(channel = "stream", poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
  public MessageSource<InputStream> ftpMessageSourceStream() {
      SftpStreamingMessageSource messageSource = new SftpStreamingMessageSource(template(), null);
      messageSource.setRemoteDirectory(env.getRequiredProperty("sftp.remote.dir"));
      messageSource.setFilter(filter());
      return messageSource;
  }
  
  public FileListFilter<LsEntry> filter() {
    CompositeFileListFilter<LsEntry> filter = new CompositeFileListFilter<>();
    Pattern pattern = Pattern.compile("(?i)^.*\\.txt$");
    filter.addFilter(new SftpRegexPatternFileListFilter(pattern));
    filter.addFilter(acceptOnceFilter());
    return filter;
  }
  
  @Bean
  public SftpRemoteFileTemplate template() {
      return new SftpRemoteFileTemplate(sftpSessionFactory);
  }
  
  @Bean
  public SftpPersistentAcceptOnceFileListFilter acceptOnceFilter() {
    SftpPersistentAcceptOnceFileListFilter filter = new SftpPersistentAcceptOnceFileListFilter(new SimpleMetadataStore(),
              "streaming");
      return filter;
  }
  
  @Bean(name = "inboundMessageHandler")
  @ServiceActivator(inputChannel = "data")
  public MessageHandler processor() {
      return new MessageHandler() {
        
        @Override
        public void handleMessage(Message<?> message) throws MessagingException {
            logger.info("Headers: "+ message.getHeaders());
            String storeObject = env.getRequiredProperty("store.upload.url")+"/upload";
            try {
              byte[] bytes = IntegrationUtil.getPayloadAsBytes(message);
              String response = IntegrationUtil.uploadClient(bytes, "request", "bajaj", storeObject, message.getHeaders().get("file_remoteFile").toString());
              logger.info("Response from object store: " + response);
            }
            catch(Exception e) {
              logger.error("Exception occurred while handling message" + e);
            }
        }
    };
  }
  
  @Bean
  @Transformer(inputChannel = "stream", outputChannel = "data")
  public org.springframework.integration.transformer.Transformer transformer() {
      return new StreamTransformer("UTF-8");
  }
}
