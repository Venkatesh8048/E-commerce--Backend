package com.example.SpringOnlineShop.service;

import com.example.SpringOnlineShop.entity.Cart;
import com.example.SpringOnlineShop.entity.Order;
import com.example.SpringOnlineShop.entity.Product;
import com.example.SpringOnlineShop.entity.User;
import com.example.SpringOnlineShop.repo.CartRepo;
import com.example.SpringOnlineShop.repo.OrderRepo;
import com.example.SpringOnlineShop.repo.ProductRepo;
import com.example.SpringOnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MailService mailService;

    public Order placeOrder(int cartId){

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("cart not found"));

        User user = cart.getUser();
        Product product = cart.getProduct();

        Order order = new Order();

        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(cart.getQuantity());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());


        order.setStatus("Placed");


        Order savedOrder = orderRepo.save(order);


        cartRepo.deleteById(cartId);


        mailService.sendMail(
                user.getEmail(),
                "Order Placed Successfully",
                "Hello " + user.getUsername() +
                        ",\n\n" +
                        "🎉 Your order has been placed successfully!\n\n" +

                        "Order Details:\n" +
                        "--------------------------\n" +
                        "Order ID: " + savedOrder.getId() + "\n" +
                        "Product: " + product.getName() + "\n" +
                        "Quantity: " + savedOrder.getQuantity() + "\n" +
                        "Total Price: ₹" + savedOrder.getTotalAmount() + "\n\n" +
                        "Shipping Address: " + user.getAddress() + "\n\n" +

                        "We will notify you once your order is shipped.\n\n" +
                        "Thank you for shopping with us! 😊"
        );

        return savedOrder;
    }

    public List<Order> getAllOrder(){
        return orderRepo.findAll();
    }

    public void cancelOrder(int orderId){

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));

        order.setStatus("CANCELED");

        orderRepo.save(order);
    }

    public void updateStatus(int orderId,String status){

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));

        order.setStatus(status);

        orderRepo.save(order);
    }
}
