package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //JPA will automatically creates its query based on this function
    //Note: price is a field in product, so jpa understands it
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    //JPA will automatically creates its query based on this function
    //Note: productName is a field in product, so jpa understands it
    //Note2: pass like this while calling: findByProductNameLikeIgnoreCase('%'+keyword+'%');
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    Product findByProductName(String productName);
}
