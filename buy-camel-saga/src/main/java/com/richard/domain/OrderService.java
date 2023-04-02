package com.richard.domain;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Header;

@ApplicationScoped
public class OrderService {

    private Set<Long> orders = new HashSet<>();

    public Set<Long> getOrders() {
        return orders;
    }

    public void newOrder(@Header("id") Long id) {
        orders.add(id);
    }

    public void cancelOrder(@Header("id") Long id) {
        orders.remove(id);
    }


}
