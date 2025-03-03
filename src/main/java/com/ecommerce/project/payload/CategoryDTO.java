package com.ecommerce.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long categoryId;

    @NotBlank
    @Size(min=5, message="Category name must contain atleast 5 characters")
    private String categoryName;
}
