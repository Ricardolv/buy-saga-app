package com.richard.domain;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CreditService {

    private long creditTotal;
    private Map<Long, Integer> creditValueMap = new HashMap<>();

    public CreditService() {
        creditTotal = 100;
    }
    
    public void newOrderValue(Long orderId, int value) {
        
        if (value > creditTotal) {
            throw new IllegalArgumentException("Insufficient funds!");        
        }

        creditTotal = creditTotal - value;
        creditValueMap.put(orderId, value);
        
    }

    public void cancelOrderValue(Long id) {

        creditTotal = creditTotal + creditValueMap.get(id);
        creditValueMap.remove(id);

    }

    public long getCreditTotal() {
        return this.creditTotal;
    }
    
}
