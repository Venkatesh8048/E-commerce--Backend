package com.example.SpringOnlineShop.controller;

import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile file
    )throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Image is required");
        }

        String uploadDir = "D:/uploads/images/";
        File uploadFolder = new File(uploadDir);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // 📌 Unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());

        Product product = new Product();

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);
        product.setImage(fileName);

        productService.addProduct(product);

        Map<String,String> response = new HashMap<>();

        response.put("message","Product added successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping(value = "/editProduct/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "image", required = false) MultipartFile file
    )throws IOException
    {

        Product product = productService.getProductById(id);

        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        if (file != null && !file.isEmpty()) {
            String uploadDir = "D:/uploads/images/";

            // Optional: Delete the old image file here to save space

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            product.setImage(fileName);
        }

        productService.updateProduct(product,id);

        Map<String,String> response = new HashMap<>();

        response.put("message","Product updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") int id){

        productService.deleteProduct(id);

        Map<String,String> response = new HashMap<>();

        response.put("message","Product deleted successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllProducts/{id}")
    public Product getProductById(
            @PathVariable int id){

        return productService.getProductById(id);

    }
}
