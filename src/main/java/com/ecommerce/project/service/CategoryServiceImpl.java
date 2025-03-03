package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.mapper.CategoryMapper;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;  // Inject the mapper


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories= categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No Category created till now");

        List<CategoryDTO> categoryDTOS=categoryMapper.categoriesToCategoryDTOs(categories);
        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;

    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if(savedCategory!=null)
            throw new APIException("Category with the name "+category.getCategoryName()+" already exists");

        categoryRepository.save(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(category);

        return "category "+categoryId+" deleted";
    }

    @Override
    public Category updateCategory(Category category,Long categoryId) {

        Optional<Category> optionalCat = categoryRepository.findById(categoryId);
        Category existingCategory = optionalCat
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        //setting individual fields is the best practice
        existingCategory.setCategoryName(category.getCategoryName());

        Category savedCategory = categoryRepository.save(existingCategory);

        return savedCategory;
    }
}
