package com.flux.core;

import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.logging.Log;
import flux.Engine;

/**
 * DW Managed Flux instance.
 *
 * @author arul@flux.ly
 */
public class FluxSchedulerManager implements Managed {

    private static final Log LOG = Log.forClass(FluxSchedulerManager.class);

    private final Engine engine;

    public FluxSchedulerManager(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void start() throws Exception {
        engine.start();
        LOG.info("Started scheduler.");
    }

    @Override
    public void stop() throws Exception {
        engine.dispose();
        LOG.info("Disposed scheduler.");
    }
}
