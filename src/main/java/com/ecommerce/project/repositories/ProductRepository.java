package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //JPA will automatically creates its query based on this function
    //Note: price is a field in product, so jpa understands it
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    //JPA will automatically creates its query based on this function
    //Note: productName is a field in product, so jpa understands it
    //Note2: pass like this while calling: findByProductNameLikeIgnoreCase('%'+keyword+'%');
    List<Product> findByProductNameLikeIgnoreCase(String keyword);

    Product findByProductName(String productName);
}
