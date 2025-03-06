package com.ecommerce.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private Long productId;
    @NotBlank
    @Size(min=3,message="Product name must contain atleast 3 characters")
    private String productName;
    private String image;
    @NotBlank
    @Size(min=6,message="Product description must contain atleast 6 characters")
    private String description;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
}
