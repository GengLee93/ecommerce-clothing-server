package com.example.Ecommerce_Clothing_Server.service;

import com.example.Ecommerce_Clothing_Server.entity.Product;
import com.example.Ecommerce_Clothing_Server.entity.ProductVariant;
import com.example.Ecommerce_Clothing_Server.entity.ProductImage;
import com.example.Ecommerce_Clothing_Server.repository.ProductRepository;
import com.example.Ecommerce_Clothing_Server.repository.ProductVariantRepository;
import com.example.Ecommerce_Clothing_Server.repository.ProductImageRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductImageRepository imageRepository;

    public ProductService(
            ProductRepository productRepository,
            ProductVariantRepository variantRepository,
            ProductImageRepository imageRepository
    ) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.imageRepository = imageRepository;
    }

    //Product

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product update) {
        Product product = getProduct(id);

        product.setName(update.getName());
        product.setDescription(update.getDescription());
        product.setPrice(update.getPrice());
        product.setStatus(update.getStatus());
        product.setRate(update.getRate());
        product.setIsList(update.getIsList());
        product.setCategoryId(update.getCategoryId());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    //Variant

    public List<ProductVariant> getVariantsByProduct(Long productId) {
        return variantRepository.findByProductId(productId);
    }

    public ProductVariant createVariant(Long productId, ProductVariant variant) {
        variant.setProductId(productId);
        return variantRepository.save(variant);
    }

    public ProductVariant updateVariantStock(Long variantId, Integer stock) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        variant.setStock(stock);
        return variantRepository.save(variant);
    }

    //Images

    public List<ProductImage> getImagesByVariant(Long variantId) {
        return imageRepository.findByVariantIdOrderBySortOrderAsc(variantId);
    }

    public ProductImage addImageToVariant(Long variantId, ProductImage image) {
        image.setVariantId(variantId);
        return imageRepository.save(image);
    }

    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}
