package com.example.SpringOnlineShop.controller;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
public class PaymentController {

        @Value("${stripe.secret.key}")
        private String secretKey;

        @PostConstruct
        public void init() {
            Stripe.apiKey = secretKey;
        }

        @PostMapping("/create-checkout-session")
        public Map<String, String> createCheckoutSession(
                @RequestBody Map<String, Object> request
        ) throws Exception {

            Long amount = Long.valueOf(request.get("amount").toString());

            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName("Product Order")
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("inr")
                            .setUnitAmount(amount * 100)
                            .setProductData(productData)
                            .build();

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://localhost:4200/payment-success")
                            .setCancelUrl("http://localhost:4200/payment-cancel")
                            .addLineItem(lineItem)
                            .build();

            Session session = Session.create(params);

            return Map.of("url", session.getUrl());
        }
    }

