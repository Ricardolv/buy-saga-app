package com.richard.infrastructure.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.richard.domain.OrderService;

@Path("buyChoreography")
public class BuyChoreographyResource {

    private final OrderService orderService;

    public BuyChoreographyResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sage() {
        
        Long id = 0L;

        buy(++id, 20);
        buy(++id, 30);
        buy(++id, 40);
        buy(++id, 25);

        return Response.ok().build();
    }

    private void buy(Long id, int value) {
        orderService.newIOrder(id, value);
    }

    
}