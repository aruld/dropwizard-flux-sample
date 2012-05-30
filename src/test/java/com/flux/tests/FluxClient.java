package com.flux.tests;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: arul
 * Date: 5/28/12
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class FluxClient {
    public static void main(String[] args) {
        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080");
        Form form = new Form();
        form.add("namespace", "one shot job");
        form.add("schedule", "+5s");
        String name = resource.path("scheduler").post(String.class, form);
        System.out.println("Scheduled " + name + " at " + new Date());
    }
}
