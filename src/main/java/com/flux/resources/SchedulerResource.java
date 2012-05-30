package com.flux.resources;

import com.yammer.dropwizard.logging.Log;
import flux.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Sample Flux scheduling resource.
 *
 * @author arul@flux.ly
 */
@Path("/scheduler")
public class SchedulerResource {

    private static final Log LOG = Log.forClass(SchedulerResource.class);

    private final Engine engine;
    private final Factory factory = Factory.makeInstance();
    private final EngineHelper helper = factory.makeEngineHelper();

    public SchedulerResource(Engine engine) {
        this.engine = engine;
    }

    @GET
    public Response version() {
        try {
            return Response.ok(engine.getVersion()).build();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return Response.serverError().build();
        }
    }

    @POST
    public Response schedule(@FormParam("namespace")String namespace, @FormParam("schedule")String timeExpression) {
        FlowChart flowChart = helper.makeFlowChart(namespace);
        TimerTrigger timer = flowChart.makeTimerTrigger("one shot timer");
        try {
            timer.setTimeExpression(timeExpression);
        } catch (EngineException e) {
            LOG.error(e.getMessage());
            return Response.serverError().build();
        }
        ConsoleAction console = flowChart.makeConsoleAction("print to console");
        console.setMessage("Timer fired at ${date D MMMM d HH:mm:ss yyyy}");
        console.println();

        timer.addFlow(console);
        try {
            String name = engine.put(flowChart);
            return Response.ok(name).build();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return Response.serverError().build();
        }
    }
}
