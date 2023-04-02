package com.richard.domain;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {

    private Set<Long> orders = new HashSet<>();
    private final CreditService creditService;

    public OrderService(CreditService creditService) {
        this.creditService = creditService;
    }


    public Set<Long> getOrders() {
        return orders;
    }


    public void newIOrder(Long id, int value) {
        orders.add(id);

        try {
            creditService.newOrderValue(id, value);
            System.out.println(String.format("Perdido %d registrado no valor de %d", id, value));
            
        } catch (IllegalArgumentException e) {

            cancelOrder(id);
            System.out.println(String.format("Perdido %d estornado no valor de %d", id, value));
        }

    }

    public void cancelOrder(Long id) {
        orders.remove(id);
    }


}
