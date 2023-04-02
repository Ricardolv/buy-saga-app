package com.richard.application.saga;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;

import com.richard.domain.CreditService;
import com.richard.domain.OrderService;

@ApplicationScoped
public class SagaRouter extends RouteBuilder {

    private final OrderService orderService;
    private final CreditService creditService;

    public SagaRouter(OrderService orderService, CreditService creditService) {
        this.orderService = orderService;
        this.creditService = creditService;
    }

    @Override
    public void configure() throws Exception {
        CamelSagaService sagaService = new InMemorySagaService();
        getContext().addService(sagaService);
            
        from("direct:saga")
        .saga().propagation(SagaPropagation.REQUIRES_NEW)
        .log("Iniciando a transação!")
        .to("direct:newOrder")
        .log("Pedido ${header.id} criado. Saga ${body}.")
        .to("direct:newOrderValue")
        .log("Credito do pedido ${header.id} no valor de BRL ${header.value} reservado para a saga ${body}")
        .to("direct:end").log("Feito!");

        //Order service
        from("direct:newOrder")
        .saga().propagation(SagaPropagation.MANDATORY)
        .compensation("direct:cancelOrder")
        .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
        .bean(orderService, "newOrder").log("Criando novo pedido com id ${header.id}");

        from("direct:cancelOrder")
        .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
        .bean(orderService, "cancelOrder").log("Pedido ${body} cancelado.");

        //Credit service
        from("direct:newOrderValue")
        .saga().propagation(SagaPropagation.MANDATORY)
        .compensation("direct:cancelOrderValue")
        .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
        .bean(creditService,"newOrderValue").log("Reservando o crédito");

        from("direct:cancelOrderValue")
        .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
        .bean(creditService, "cancelOrderValue").log("Credito compensado para a saga ${body}");

        //End
        from("direct:end")
        .saga().propagation(SagaPropagation.MANDATORY)
        .choice()
        .end();

    }
    
}
