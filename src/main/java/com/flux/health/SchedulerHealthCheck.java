package com.flux.health;

import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.core.HealthCheck;
import flux.Engine;

/**
 * Flux health checker.
 *
 * @author arul@flux.ly
 */
public class SchedulerHealthCheck extends HealthCheck {

    private static final Log LOG = Log.forClass(SchedulerHealthCheck.class);

    private final Engine engine;

    public SchedulerHealthCheck(Engine engine) {
        super("flux");
        this.engine = engine;
    }

    @Override
    protected Result check() throws Exception {
        try {
            engine.ping();
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy(e.getMessage());
        }
    }
}
