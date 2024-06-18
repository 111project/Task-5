package hiber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "hiber")
public class AppConfig {

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());
      factoryBean.setPackagesToScan("hiber.model");
      factoryBean.setHibernateProperties(hibernateProperties());
      return factoryBean;
   }

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("org.postgresql.Driver");
      dataSource.setUrl("jdbc:postgresql://localhost:5432/myDatabase");
      dataSource.setUsername("postgres");
      dataSource.setPassword("postgres");
      return dataSource;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
      properties.put("hibernate.show_sql", "true");
      properties.put("hibernate.hbm2ddl.auto", "update");
      return properties;
   }
}
