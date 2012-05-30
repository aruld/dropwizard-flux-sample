package com.flux;

import com.flux.core.FluxSchedulerManager;
import com.flux.health.SchedulerHealthCheck;
import com.flux.resources.SchedulerResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.logging.Log;
import flux.*;

import java.rmi.RemoteException;

/**
 * DW Flux scheduler service.
 *
 * @author arul@flux.ly
 */
public class SchedulerService extends Service<SchedulerConfiguration> {

    private static final Log LOG = Log.forClass(SchedulerService.class);
    private final Factory factory = Factory.makeInstance();

    public static void main(String... args) throws Exception {
        new SchedulerService("flux").run(new String[]{"server", "src/main/resources/flux.yaml"});
    }

    private SchedulerService(String name) {
        super(name);
    }

    @Override
    protected void initialize(SchedulerConfiguration schedulerConfig,
                              Environment environment) throws EngineException, RemoteException {

        final DatabaseConfiguration dbConfig = schedulerConfig.getFluxConfig().getDatabaseConfig();
        final FluxConfiguration fluxConfig = schedulerConfig.getFluxConfig();
        Configuration config;
        try {
            config = factory.makeConfiguration();
            DatabaseType dbType = dbConfig.getDatabaseType();
            config.setDatabaseType(dbType);
            if (!(dbType.equals(DatabaseType.H2) || dbType.equals(DatabaseType.DERBY))) {
                config.setDriver(dbConfig.getDriver());
                config.setUrl(dbConfig.getUrl());
                config.setJdbcUsername(dbConfig.getJdbcUser());
                config.setJdbcPassword(dbConfig.getJdbcPassword());
            }
            config.setMaxConnections(dbConfig.getMaxConnections());
            config.setConcurrencyLevel(dbConfig.getConcurrencyLevel());
            config.setSecurityEnabled(fluxConfig.isSecurityEnabled());
            config.setRegistryPort(fluxConfig.getRegistryPort());
        } catch (EngineException e) {
            LOG.error(e, "Error making configuration. Reason : ", e.getMessage());
            throw e;
        }

        boolean enableRemoteSecurity = false;
        if (fluxConfig.isSecurityEnabled() && fluxConfig.isRmiServer()) {
            enableRemoteSecurity = true;
        }

        if (enableRemoteSecurity) {
            config.setRmiServer(false);
        } else {
            config.setRmiServer(fluxConfig.isRmiServer());
        }

        //verify config
        config.verify();

        Engine engine = factory.makeEngine(config);

        // enable remote security
        if (enableRemoteSecurity) {
            RemoteSecurity remoteSecurity = factory.makeRemoteSecurity(config, engine);
            engine = remoteSecurity.login(fluxConfig.getUser(), fluxConfig.getPassword());
        }

        FluxSchedulerManager schedulerManager = new FluxSchedulerManager(engine);
        environment.manage(schedulerManager);
        environment.addHealthCheck(new SchedulerHealthCheck(engine));
        environment.addResource(new SchedulerResource(engine));

    }
}
