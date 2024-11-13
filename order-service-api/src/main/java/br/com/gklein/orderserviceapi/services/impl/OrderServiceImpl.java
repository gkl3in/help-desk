package br.com.gklein.orderserviceapi.services.impl;

import br.com.gklein.orderserviceapi.entities.Order;
import br.com.gklein.orderserviceapi.mapper.OrderMapper;
import br.com.gklein.orderserviceapi.repositories.OrderRepository;
import br.com.gklein.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;
import static models.enums.OrderStatusEnum.CLOSED;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public Order findById(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + Order.class.getSimpleName()
                ));
    }

    @CacheEvict(value = "orders", allEntries = true)
    @Override
    public void save(CreateOrderRequest request) {
        final var entity = repository.save(mapper.fromRequest(request));

        log.info("Order created: {}", entity);
    }

    @Override
    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponse update(final Long id, UpdateOrderRequest request) {

        Order entity = findById(id);
        entity = mapper.fromRequest(entity, request);

        if (entity.getStatus().equals(CLOSED)) {
            entity.setClosedAt(now());
        }

        return mapper.fromEntity(repository.save(entity));
    }
}