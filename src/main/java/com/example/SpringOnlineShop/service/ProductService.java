package com.example.SpringOnlineShop.service;

import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public void addProduct(Product product){
        productRepo.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public void updateProduct(Product product,int id){
        Product p = productRepo.findById(id).get();

        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setDescription(product.getDescription());
        p.setCategory(product.getCategory());
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            p.setImage(product.getImage());
        }


        productRepo.save(p);
    }

    public void deleteProduct(int id){
        productRepo.deleteById(id);
    }

    public Product getProductById(int id){
        Product product = productRepo.findById(id).get();
        return product;
    }
}
