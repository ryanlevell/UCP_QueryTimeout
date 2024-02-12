package org.levell.ucpdemo.demo;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceImpl;
import org.levell.ucpdemo.demo.app.TheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    TheService theService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(theService.doStuff().size());
    }

    @Configuration
    public static class Config {

        @Bean
        public PoolDataSource dataSource() throws Exception {
            PoolDataSource poolDataSource = new PoolDataSourceImpl();
            poolDataSource.setConnectionFactoryClassName("org.h2.Driver");
            poolDataSource.setURL("jdbc:h2:file:./testdb");
            poolDataSource.setUser("sa");
            poolDataSource.setPassword("password");

            poolDataSource.setQueryTimeout(2);
            poolDataSource.setTimeoutCheckInterval(1);
            poolDataSource.setAbandonedConnectionTimeout(1);

            return poolDataSource;
        }

        @Bean
        public LocalSessionFactoryBean sessionFactory(PoolDataSource dataSource) {
            LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setPackagesToScan("org.levell.ucpdemo.demo.app");
            sessionFactory.setHibernateProperties(hibernateProperties());

            return sessionFactory;
        }

        private Properties hibernateProperties() {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty(
                    "hibernate.hbm2ddl.auto", "none");

            return hibernateProperties;
        }
    }
}
