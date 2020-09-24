package mk.learning.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Bean
    public SpringLiquibase getSpringLiquibaseBean() {
        log.info("creating liquibase bean");
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:db/changelog/changelog-master.yml");
        springLiquibase.setDataSource(getDataSource());
        return springLiquibase;
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(org.h2.jdbcx.JdbcDataSource.class);
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:~/oauth-db");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password(" ");
        return dataSourceBuilder.build();
    }

}
