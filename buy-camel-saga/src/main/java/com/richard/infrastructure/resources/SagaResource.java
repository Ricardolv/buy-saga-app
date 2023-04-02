package com.richard.infrastructure.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;

@Path("buyCamel")
public class SagaResource {

    @Inject
    CamelContext camelContext;

    @GET
    @Path("test")
    public Response saga() {

        Long id = 0L;

        buy(++id, 20);
        buy(++id, 30);
        buy(++id, 30);
        buy(++id, 25);

        return Response.ok().build();
    }

    private void buy(Long id, int value) {
        System.out.println("Pedido: " + id + " valor: " + value);

        try{
            camelContext.createFluentProducerTemplate()
            .to("direct:saga")
            .withHeader("id", id)
            .withHeader("value", value)
            .request();
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

}
