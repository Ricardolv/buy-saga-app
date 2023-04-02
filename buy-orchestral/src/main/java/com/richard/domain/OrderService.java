package com.richard.domain;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {

    private Set<Long> orders = new HashSet<>();

    public Set<Long> getOrders() {
        return orders;
    }

    public void newIOrder(Long id) {
        orders.add(id);
    }

    public void cancelOrder(Long id) {
        orders.remove(id);
    }


}
