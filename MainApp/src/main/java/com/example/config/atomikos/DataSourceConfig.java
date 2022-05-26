package com.example.config.atomikos;

import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
        basePackages = "com.example.repository",
        entityManagerFactoryRef = "entityManager"
)
public class DataSourceConfig {

    private final JpaVendorAdapter adapter;

    @Autowired
    public DataSourceConfig(JpaVendorAdapter adapter) {
        this.adapter = adapter;
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                adapter,
                additionalJpaProperties(),
                null
        );
    }

    @Primary
    @Bean(name = "entityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean entityManager() throws Throwable {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(dataSource(dataSourceProperties()));
        entityManager.setJpaVendorAdapter(adapter);
        entityManager.setPackagesToScan("com.example.entity");
        entityManager.setPersistenceUnitName("persistenceUnit");
        entityManager.setJpaPropertyMap(additionalJpaProperties());
        return entityManager;
    }

    @Primary
    @Bean("dataSourceProperties")
    @ConfigurationProperties(prefix = "blps.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        PGXADataSource ds = new PGXADataSource();
        ds.setUrl(dataSourceProperties.getUrl());
        ds.setUser(dataSourceProperties.getUsername());
        ds.setPassword(dataSourceProperties.getPassword());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(ds);
        xaDataSource.setUniqueResourceName("XADataSource");
        return xaDataSource;
    }

    private Map<String, ?> additionalJpaProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        map.put("hibernate.show_sql", "false");
        map.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        map.put("hibernate.transaction.jta.platform", "com.atomikos.icatch.jta.hibernate4.AtomikosPlatform");
        map.put("javax.persistence.transactionType", "JTA");
        return map;
    }
}