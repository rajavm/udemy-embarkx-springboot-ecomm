package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    //This is for SELLER side. Multiple products can be sold by one seller associated with products
    @ManyToOne
    @JoinColumn(name="seller_id")
    private User user;

    @OneToMany(mappedBy = "product",
    cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    fetch=FetchType.EAGER)
    private List<CartItem> products = new ArrayList<>();
}
