package com.eclub.repository;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.Objects;

@Configuration
@EnableR2dbcRepositories
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RepositoryConfiguration extends AbstractR2dbcConfiguration {

    private final Environment environment;

    @Override
    @Nonnull
    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host(Objects.requireNonNull(environment.getProperty("db.host"), "DB host"))
                .database(Objects.requireNonNull(environment.getProperty("db.name"), "DB name"))
                .username(Objects.requireNonNull(environment.getProperty("db.username"), "DB username"))
                .password(Objects.requireNonNull(environment.getProperty("db.password"), "DB password"))
                .build());
    }
}
