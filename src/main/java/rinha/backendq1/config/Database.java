package rinha.backendq1.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration

public class Database {

    @Bean
    public DataSource dataSource() {
        try {
            Class.forName("org.postgresql.ds.PGSimpleDataSource");
        } catch (Exception e) {
        }

        DataSourceBuilder datasource = DataSourceBuilder.create();

        datasource.driverClassName("org.postgresql.Driver");
        datasource.url("jdbc:postgresql://localhost:5432/rinha?user=rinha&password=rinha");
        return datasource.build();
    }
}
