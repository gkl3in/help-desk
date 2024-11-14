package br.com.gklein.orderserviceapi.services;

import br.com.gklein.orderserviceapi.entities.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

public interface OrderService {

    Order findById(final Long id);

    void save(CreateOrderRequest request);

    OrderResponse update(final Long id, UpdateOrderRequest request);

    void deleteById(final Long id);
}