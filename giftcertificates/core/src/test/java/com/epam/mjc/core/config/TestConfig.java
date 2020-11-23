package com.epam.mjc.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.sql.DataSource;

@Configuration
//@TestConfiguration
@ComponentScan(basePackages = {"com.epam.mjc.core.dao","com.epam.mjc.core.service.help","com.epam.mjc.core.service.validator"})
@SqlGroup({
        @Sql(scripts = "/drop_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/create_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/fill_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TestConfig {

    @Bean
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:create_tables.sql")
                .addScript("classpath:fill_tables.sql")
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDataSource());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(embeddedDataSource());
    }
}
