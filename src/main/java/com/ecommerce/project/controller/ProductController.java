package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController{

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,@PathVariable Long categoryId){
        ProductDTO savedProductDTO = productService.addProduct(categoryId,productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);
    }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue=AppConstants.SORT_CATEGORIES_BY,required=false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue=AppConstants.SORT_DIR,required=false) String sortOrder){

        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
            @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue=AppConstants.SORT_CATEGORIES_BY,required=false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue=AppConstants.SORT_DIR,required=false) String sortOrder){

        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                 @RequestParam(name="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
                                                                 @RequestParam(name="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
                                                                 @RequestParam(name="sortBy",defaultValue=AppConstants.SORT_CATEGORIES_BY,required=false) String sortBy,
                                                                 @RequestParam(name="sortOrder",defaultValue=AppConstants.SORT_DIR,required=false) String sortOrder){

        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId,productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductDTO);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProductDTO = productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedProductDTO);
    }
}
