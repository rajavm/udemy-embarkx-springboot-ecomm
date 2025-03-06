package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.mapper.ProductMapper;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final FileService fileService;

    private final String path;

    //reads project.image from application.properties and construtor injects
    public ProductServiceImpl(@Value("${project.image}")String path,ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper, FileService fileService) {
        this.path = path;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.fileService = fileService;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product = productMapper.productDTOToProduct(productDTO);
        product.setImage("default.png");
        product.setCategory(category);
        Double specialPrice = product.getPrice()-((product.getDiscount()*0.01)* product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(product);

        return productMapper.productToProductDTO(savedProduct);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        /*Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);*/

        //Page<Product> productPage = productRepository.findAll(pageDetails);
        //List<Product> productList = productPage.getContent();
        List<Product> productList = productRepository.findAll();
        if(productList.isEmpty())
            throw new APIException("No Product created till now");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productMapper.productsToProductDTOs(productList));
        /*productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());*/

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> productList = productRepository.findByCategoryOrderByPriceAsc(category);
        if(productList.isEmpty())
            throw new APIException("No Product created till now");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productMapper.productsToProductDTOs(productList));
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        //makesure to pass with %keyword% findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<Product> productList = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        if(productList.isEmpty())
            throw new APIException("No Product created till now");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productMapper.productsToProductDTOs(productList));
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product","productId",productId));

        Product product = productMapper.productDTOToProduct(productDTO);

        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepository.save(productFromDb);

        return productMapper.productToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //GEt product from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product","productId",productId));
        //upload image to server
        //get the file name of uploaded image
        String fileName = fileService.uploadImage(path,image);

        //updating the new file name to the product
        productFromDb.setImage(fileName);

        //save updated product
        Product updatedProduct = productRepository.save(productFromDb);

        //return DTO after mapping product to DTO
        return productMapper.productToProductDTO(updatedProduct);
    }
}
