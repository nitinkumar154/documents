package com.innoviti.emi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
public class RemoteSftpConfiguration {

  @Autowired
  private Environment env;
  
  @Bean
  public SessionFactory<LsEntry> sftpSessionFactory() {
    DefaultSftpSessionFactory sf = new DefaultSftpSessionFactory();
    sf.setHost(env.getRequiredProperty("sftp.ipaddress"));
    sf.setPort(Integer.parseInt(env.getRequiredProperty("sftp.port")));
    sf.setUser(env.getRequiredProperty("sftp.username"));
    sf.setPassword(env.getRequiredProperty("sftp.password"));
    sf.setAllowUnknownKeys(true);
    return new CachingSessionFactory<LsEntry>(sf);
  }
}
