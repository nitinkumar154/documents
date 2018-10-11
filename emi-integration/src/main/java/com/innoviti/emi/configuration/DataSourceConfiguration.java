package com.innoviti.emi.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.innoviti.emi.repository"})
public class DataSourceConfiguration {
  @Autowired
  private Environment env;

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    HikariConfig dataSourceConfig = new HikariConfig();
    dataSourceConfig.setDriverClassName(env.getRequiredProperty("db.driver"));
    dataSourceConfig.setJdbcUrl(env.getRequiredProperty("db.url"));
    dataSourceConfig.setUsername(env.getRequiredProperty("db.username"));
    dataSourceConfig.setPassword(env.getRequiredProperty("db.password"));
    dataSourceConfig.setAutoCommit(false);
    String maxPoolSize = env.getRequiredProperty("db.max.poolsize");
    String minIdle = env.getRequiredProperty("db.min.idle");
    dataSourceConfig.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
    dataSourceConfig.setMinimumIdle(Integer.parseInt(minIdle));
    return new HikariDataSource(dataSourceConfig);
  }
  
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.innoviti.emi.entity");

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
    jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
    jpaProperties.put("hibernate.ejb.naming_strategy",
        env.getRequiredProperty("hibernate.ejb.naming_strategy"));
    jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
    jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);
    return entityManagerFactoryBean;
  }
  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }
}
