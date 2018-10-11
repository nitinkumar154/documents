package com.innoviti.emi.configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class UtilConfig {

  private Logger logger = LoggerFactory.getLogger(UtilConfig.class);
  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    return new MappingJackson2HttpMessageConverter(mapper);
  }
  
  @Bean
  public RestTemplate restTemplate() {
    SSLContext sslContext = null;
    try{
        sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          return true;
        }
      }).build();
    } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
      logger.error("SSL key ", e);
    }
    CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
    .build();
    HttpComponentsClientHttpRequestFactory requestFactory  = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    requestFactory.setReadTimeout(5 * 1000);
    requestFactory.setConnectTimeout(5 * 1000);
    return new RestTemplate(requestFactory);
  }
}
