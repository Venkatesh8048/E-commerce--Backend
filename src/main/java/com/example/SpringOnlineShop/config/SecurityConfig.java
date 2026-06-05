package com.example.SpringOnlineShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(c->c.disable())
                .formLogin(form -> form.disable())   // ⭐ disable default login
                .httpBasic(basic -> basic.disable()) // optional
                .authorizeHttpRequests(req->req
                        .requestMatchers(
                                "/login",
                                "/addUser",
                                "/email-exists",
                                "/getAllUsers",
                                "/updateUser/{id}",
                                "/deleteUser/{id}",
                                "/addProduct",
                                "/getAllProducts",
                                "/editProduct/{id}",
                                "/deleteProduct/{id}",
                                "/getAllProducts/{id}",
                                "/images/**",
                                "/addToCart/{productId}",
                                "/getCart",
                                "/removeCartItem/{cartId}",
                                "/removeCart/{cartId}",
                                "/addCartItem/{cartId}",
                                "/placedOrder/**",
                                "/updateOrderStatus/{orderId}",
                                "/allOrder",
                                "/cancelOrder/{orderId}",
                                "/payment/**"


                        ).permitAll()
                        .requestMatchers(
                                "/cancelOrder/**"

                        ).hasRole("USER")

                        .anyRequest().authenticated()
                )
        ;


        return http.build();
    }





    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
