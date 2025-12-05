package com.example.Ecommerce_Clothing_Server.controller;

import com.example.Ecommerce_Clothing_Server.entity.Product;
import com.example.Ecommerce_Clothing_Server.entity.ProductVariant;
import com.example.Ecommerce_Clothing_Server.entity.ProductImage;
import com.example.Ecommerce_Clothing_Server.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Product API

    // GET /api/products
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    // POST /api/products
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    //Variant API

    // GET /api/products/{id}/variants（size / color / stock）
    @GetMapping("/{id}/variants")
    public List<ProductVariant> getVariants(@PathVariable Long id) {
        return productService.getVariantsByProduct(id);
    }

    // POST /api/products/{id}/variants// 新增variant
    @PostMapping("/{id}/variants")
    public ProductVariant createVariant(@PathVariable Long id,
                                        @RequestBody ProductVariant variant) {
        return productService.createVariant(id, variant);
    }

    // PATCH /api/products/variants/{variantId}/stock?value=10// 更新variant的庫存

    @PatchMapping("/variants/{variantId}/stock")
    public ProductVariant updateStock(@PathVariable Long variantId,
                                      @RequestParam("value") Integer stock) {
        return productService.updateVariantStock(variantId, stock);
    }

    // Image API

    // GET /api/products/variants/{variantId}/images// 取得variant圖片
    @GetMapping("/variants/{variantId}/images")
    public List<ProductImage> getImages(@PathVariable Long variantId) {
        return productService.getImagesByVariant(variantId);
    }

    // POST /api/products/variants/{variantId}/images// 新增圖片到variant
    @PostMapping("/variants/{variantId}/images")
    public ProductImage addImage(@PathVariable Long variantId,
                                 @RequestBody ProductImage image) {
        return productService.addImageToVariant(variantId, image);
    }

    // DELETE /api/products/images/{imageId}// 刪除圖片
    @DeleteMapping("/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        productService.deleteImage(imageId);
    }
}
