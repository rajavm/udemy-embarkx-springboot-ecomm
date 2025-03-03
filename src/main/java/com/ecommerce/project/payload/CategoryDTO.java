package com.ecommerce.project.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
}
