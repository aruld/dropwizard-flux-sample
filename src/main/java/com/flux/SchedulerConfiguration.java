package com.flux;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Flux engine configuration wrapper.
 *
 * @author arul@flux.ly
 */
public class SchedulerConfiguration extends Configuration {

    @NotNull
    @JsonProperty("flux")
    private FluxConfiguration fluxConfig = new FluxConfiguration();

    public FluxConfiguration getFluxConfig() {
        return fluxConfig;
    }

}
