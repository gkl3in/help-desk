package br.com.gklein.orderserviceapi.controllers.impl;

import br.com.gklein.orderserviceapi.controllers.OrderController;
import br.com.gklein.orderserviceapi.mapper.OrderMapper;
import br.com.gklein.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;
    private final OrderMapper mapper;
    private final Environment environment;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        var port = environment.getProperty("local.server.port");
        log.info("Buscando ordem de serviço pelo id: {} na porta {}", id, port);

        return ResponseEntity.ok().body(
                mapper.fromEntity(service.findById(id))
        );
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(final Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}