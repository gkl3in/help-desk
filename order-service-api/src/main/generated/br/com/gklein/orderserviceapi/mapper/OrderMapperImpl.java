package br.com.gklein.orderserviceapi.mapper;

import br.com.gklein.orderserviceapi.entities.Order;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T06:34:06-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order fromRequest(CreateOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        if ( request.status() != null ) {
            order.status( mapStatus( request.status() ) );
        }
        if ( request.requesterId() != null ) {
            order.requesterId( request.requesterId() );
        }
        if ( request.customerId() != null ) {
            order.customerId( request.customerId() );
        }
        if ( request.title() != null ) {
            order.title( request.title() );
        }
        if ( request.description() != null ) {
            order.description( request.description() );
        }

        order.createdAt( mapCreatedAt() );

        return order.build();
    }

    @Override
    public Order fromRequest(Order entity, UpdateOrderRequest request) {
        if ( request == null ) {
            return entity;
        }

        if ( request.status() != null ) {
            entity.setStatus( mapStatus( request.status() ) );
        }
        if ( request.requesterId() != null ) {
            entity.setRequesterId( request.requesterId() );
        }
        if ( request.customerId() != null ) {
            entity.setCustomerId( request.customerId() );
        }
        if ( request.title() != null ) {
            entity.setTitle( request.title() );
        }
        if ( request.description() != null ) {
            entity.setDescription( request.description() );
        }

        return entity;
    }

    @Override
    public OrderResponse fromEntity(Order save) {
        if ( save == null ) {
            return null;
        }

        String id = null;
        String requesterId = null;
        String customerId = null;
        String title = null;
        String description = null;
        String status = null;
        String createdAt = null;
        String closedAt = null;

        if ( save.getId() != null ) {
            id = String.valueOf( save.getId() );
        }
        if ( save.getRequesterId() != null ) {
            requesterId = save.getRequesterId();
        }
        if ( save.getCustomerId() != null ) {
            customerId = save.getCustomerId();
        }
        if ( save.getTitle() != null ) {
            title = save.getTitle();
        }
        if ( save.getDescription() != null ) {
            description = save.getDescription();
        }
        if ( save.getStatus() != null ) {
            status = save.getStatus().name();
        }
        if ( save.getCreatedAt() != null ) {
            createdAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( save.getCreatedAt() );
        }
        if ( save.getClosedAt() != null ) {
            closedAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( save.getClosedAt() );
        }

        OrderResponse orderResponse = new OrderResponse( id, requesterId, customerId, title, description, status, createdAt, closedAt );

        return orderResponse;
    }

    @Override
    public List<OrderResponse> fromEntities(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>( orders.size() );
        for ( Order order : orders ) {
            list.add( fromEntity( order ) );
        }

        return list;
    }
}
