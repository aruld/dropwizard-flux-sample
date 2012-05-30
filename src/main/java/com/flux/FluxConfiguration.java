package com.flux;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Flux engine configuration.
 *
 * @author arul@flux.ly
 */
public class FluxConfiguration {
    @JsonProperty("server")
    private boolean rmiServer = true;

    @Min(1)
    @Max(65535)
    @JsonProperty("port")
    private int registryPort = 1099;

    @JsonProperty("security")
    private boolean securityEnabled = true;

    @JsonProperty
    private String user;

    @JsonProperty
    private String password;

    @JsonProperty("dbConfig")
    private DatabaseConfiguration databaseConfig = new DatabaseConfiguration();

    public DatabaseConfiguration getDatabaseConfig() {
        return databaseConfig;
    }

    public boolean isRmiServer() {
        return rmiServer;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
