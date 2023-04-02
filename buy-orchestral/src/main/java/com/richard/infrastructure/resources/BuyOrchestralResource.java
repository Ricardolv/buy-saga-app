package com.richard.infrastructure.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.richard.domain.CreditService;
import com.richard.domain.OrderService;

@Path("buyOrchestral")
public class BuyOrchestralResource {

    private final CreditService creditService;
    private final OrderService orderService;

    public BuyOrchestralResource(CreditService creditService, OrderService orderService) {
        this.creditService = creditService;
        this.orderService = orderService;
    }


    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sage() {
        
        Long id = 0L;

        buy(++id, 20);
        buy(++id, 30);
        buy(++id, 30);
        buy(++id, 25);

        return Response.ok().build();
    }

    private void buy(Long id, int value) {

        orderService.newIOrder(id);

        try {
            creditService.newOrderValue(id, value);

            System.out.println(String.format("Perdido %d registrado no valor de %d. Saldo disponivel: %d", id, value, creditService.getCreditTotal()));
        } catch (IllegalArgumentException e) {
            orderService.cancelOrder(id);
            
            System.out.println(String.format("Perdido %d estornado no valor de %d", id, value));
        }
        

        

    }

    
}
