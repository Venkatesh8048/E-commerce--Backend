package com.example.SpringOnlineShop.repo;

import com.example.SpringOnlineShop.entity.Cart;
import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

     Optional<Cart> findByUserAndProduct(User user, Product product);

     Optional<Cart> findByUserIdAndProductId(int userId, int productId);
}
