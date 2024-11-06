package br.com.gklein.orderserviceapi.services;

import models.requests.CreateOrderRequest;

public interface OrderService {

    void save(CreateOrderRequest request);
}