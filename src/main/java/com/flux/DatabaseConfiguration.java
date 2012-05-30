package com.flux;

import flux.DatabaseType;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Flux engine database configuration.
 *
 * @author arul@flux.ly
 */
public class DatabaseConfiguration {

    @JsonProperty
    @NotNull
    private DatabaseType databaseType = DatabaseType.H2;

    @JsonProperty
    private int maxConnections = 15;

    @JsonProperty
    private int concurrencyLevel = 10;

    @JsonProperty
    private String driver;

    @JsonProperty
    private String url;

    @JsonProperty
    private String jdbcUser;

    @JsonProperty
    private String jdbcPassword;

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public int getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }
}
