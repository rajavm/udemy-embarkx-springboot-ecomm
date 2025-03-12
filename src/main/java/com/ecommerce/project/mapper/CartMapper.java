package com.ecommerce.project.mapper;

import com.ecommerce.project.model.Cart;
import com.ecommerce.project.payload.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // Map single Cart to CartDTO
    CartDTO cartToCartDTO(Cart cart);

    // Map single CartDTO to Cart
    Cart cartDTOToCart(CartDTO cartDTO);
}
