package com.ecommerce.project.mapper;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //Map single Product Model to single ProductDTO
    ProductDTO productToProductDTO(Product product);

    //Map single ProductDTO to single Product Model
    Product productDTOToProduct(ProductDTO productDTO);

    //Map list of Product Model to Product DTO
    List<ProductDTO> productsToProductDTOs(List<Product> products);
}
