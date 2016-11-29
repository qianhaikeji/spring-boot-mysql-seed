package com.qhkj.seed.config;

import java.util.Properties;
import javax.sql.DataSource;

import com.qhkj.seed.Application;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
public class MysqlConfig{

	@Value("${spring.dataSource.driverClassName}")
	private String driver;
	@Value("${spring.dataSource.url}")
	private String url;
	@Value("${spring.dataSource.username}")
	private String username;
	@Value("${spring.dataSource.password}")
	private String password;
	@Value("${spring.hibernate.dialect}")
	private String dialect;
	@Value("${spring.hibernate.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	@Value("${spring.hibernate.show_sql}")
	private Boolean showSql;
	@Value("${spring.hibernate.persistenceUnitName}")
	private String persistenceUnitName;
	@Value("${spring.hibernate.packagesToScan}")
	private String packagesToScan; 

	@Bean
	public DataSource configureDataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driver);
		config.setJdbcUrl(url);
		config.addDataSourceProperty("user", username);
		config.addDataSourceProperty("password", password);

		config.addDataSourceProperty("useUnicode", "true");
		config.addDataSourceProperty("useSSL", "false");
		config.addDataSourceProperty("characterEncoding", "utf8");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.addDataSourceProperty("useServerPrepStmts", "true");

		return new HikariDataSource(config);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceUnitName(persistenceUnitName);
		entityManagerFactoryBean.setDataSource(configureDataSource());
		entityManagerFactoryBean.setPackagesToScan(packagesToScan);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
		jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
		jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, showSql);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

}
