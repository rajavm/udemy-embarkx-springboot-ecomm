package com.ecommerce.project.mapper;

import com.ecommerce.project.model.OrderItem;
import com.ecommerce.project.payload.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    // Map single OrderItem to OrderItemDTO
    OrderItemDTO orderItemToOrderItemDTO(OrderItem order);

    // Map single OrderItemDTO to OrderItem
    OrderItem orderItemDTOToOrderItem(OrderItemDTO orderDTO);
}
