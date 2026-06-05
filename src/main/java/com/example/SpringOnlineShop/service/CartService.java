package com.example.SpringOnlineShop.service;

import com.example.SpringOnlineShop.entity.Cart;
import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.entity.User;
import com.example.SpringOnlineShop.repo.CartRepo;
import com.example.SpringOnlineShop.repo.ProductRepo;
import com.example.SpringOnlineShop.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    public void addToCart(int userId, int productId){

        User user = userRepo.findById(userId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();
        Optional<Cart> existing = cartRepo.findByUserAndProduct(user, product);

        if(existing.isPresent()){
            Cart cart = existing.get();
            int newQuantity = cart.getQuantity()+1;

            cart.setQuantity(newQuantity);
            cart.setTotalPrice(product.getPrice() * newQuantity);

            cartRepo.save(cart);
        }
        else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setTotalPrice(product.getPrice());

            cartRepo.save(cart);
        }
    }

    public List<Cart> getCartByUserId(){
        return cartRepo.findAll();
    }

    @Transactional
    public void removeQuantity(int cartId){

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        int newQuantity = cart.getQuantity() - 1;

        if(newQuantity <= 0){
            cartRepo.delete(cart);
        } else {
            cart.setQuantity(newQuantity);
            cart.setTotalPrice(newQuantity * cart.getProduct().getPrice());
        }
    }

    @Transactional
    public void addQuantity(int cartId){

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        int newQuantity = cart.getQuantity() + 1;
            cart.setQuantity(newQuantity);
            cart.setTotalPrice(newQuantity * cart.getProduct().getPrice());

    }

    public void deleteCart(int id){
        cartRepo.deleteById(id);
    }



}
