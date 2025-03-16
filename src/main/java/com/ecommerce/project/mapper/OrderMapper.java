package com.ecommerce.project.mapper;

import org.mapstruct.Mapper;
import com.ecommerce.project.model.Order;
import com.ecommerce.project.payload.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Map single Order to OrderDTO
    OrderDTO orderToOrderDTO(Order order);

    // Map single OrderDTO to Order
    Order orderDTOToOrder(OrderDTO orderDTO);
}
