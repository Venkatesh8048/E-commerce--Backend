package com.example.SpringOnlineShop.controller;

import com.example.SpringOnlineShop.entity.Cart;
import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.entity.User;
import com.example.SpringOnlineShop.service.CartService;
import com.example.SpringOnlineShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/addToCart/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable("productId") int productId,
            @RequestParam("userId") int userId
    ) {
        cartService.addToCart(userId, productId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product added to cart successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCart")
    public List<Cart> getAllCart(){
        return cartService.getCartByUserId();
    }

    @PutMapping("/removeCartItem/{cartId}")
    public ResponseEntity<?> removeProductQuantity(@PathVariable int cartId){

        cartService.removeQuantity(cartId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product quantity updated");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/addCartItem/{cartId}")
    public ResponseEntity<?> addProductQuantity(@PathVariable int cartId){

        cartService.addQuantity(cartId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product quantity updated");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/removeCart/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") int id){
        cartService.deleteCart(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Cart deleted successfully");

        return ResponseEntity.ok(response);
    }
}
