package com.ecommerce.project.mapper;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // Map single Category to CategoryDTO
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "categoryName", target = "categoryName")
    CategoryDTO categoryToCategoryDTO(Category category);

    // Map List of Category to List of CategoryDTO
    List<CategoryDTO> categoriesToCategoryDTOs(List<Category> categories);

    // Create CategoryResponse from List of CategoryDTO
    default CategoryResponse toCategoryResponse(List<Category> categories) {
        if (categories == null) {
            return new CategoryResponse(Collections.emptyList());
        }
        return new CategoryResponse(categoriesToCategoryDTOs(categories));
    }
}
