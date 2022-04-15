package com.itechart.esm.repository.jdbc_template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TestSpringJdbcConfiguration {
	private final String dbUrl = "jdbc:postgresql://app-db/esm-database-test";
	private final String userName = "postgres";
	private final String password = "root";
	private final String driverClassName = "org.postgresql.Driver";

	@Bean
	public DataSource postgresqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public JdbcTemplate applicationDataConnection() {
		return new JdbcTemplate(postgresqlDataSource());
	}
}