package com.richard.domain;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Header;


@ApplicationScoped
public class CreditService {

    private int creditTotal;
    private Map<Long, Integer> creditValueMap = new HashMap<>();

    public CreditService() {
        creditTotal = 100;
    }
    
    public void newOrderValue(@Header("id") Long orderId, @Header("value") int value) {
        
        if (value > creditTotal) {
            throw new IllegalArgumentException("Insufficient funds!");        
        }

        creditTotal = creditTotal - value;
        creditValueMap.put(orderId, value);
        
    }

    public void cancelOrderValue(@Header("id") Long id) {
        System.out.println("PedidoValor falhou. Iniciando cancelamento do pedido.");
    }

    public int getCreditTotal() {
        return this.creditTotal;
    }

}
