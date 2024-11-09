package br.com.gklein.orderserviceapi.mapper;

import br.com.gklein.orderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

import static models.enums.OrderStatusEnum.toEnum;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapStatus")
    @Mapping(target = "createdAt", expression = "java(mapCreatedAt())")
    Order fromRequest(CreateOrderRequest request);

    OrderResponse fromEntity(Order save);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return toEnum(status);
    }

    default LocalDateTime mapCreatedAt() {
        return LocalDateTime.now();
    }

    List<OrderResponse> fromEntities(List<Order> orders);
}