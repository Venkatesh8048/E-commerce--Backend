package com.example.SpringOnlineShop.controller;

import com.example.SpringOnlineShop.dto.LoginRequest;
import com.example.SpringOnlineShop.entity.User;
import com.example.SpringOnlineShop.repo.UserRepo;
import com.example.SpringOnlineShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        User user = userRepo.findByUsername(request.getUsername());

        if(user == null || !passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login Successful");
        response.put("userId",  String.valueOf(user.getId()));
        response.put("role",user.getRole());
        response.put("phone", user.getPhone());
        response.put("address", user.getAddress());
        response.put("email", user.getEmail());
        response.put("username",user.getUsername());
        response.put("password", request.getPassword());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){

        userService.addUser(user);

        Map<String,String> response = new HashMap<>();

        response.put("message","User added successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/email-exists")
    public boolean emailExits(
            @RequestParam String email,
            @RequestParam(required = false) Integer userId
            ){

        User user = userRepo.findByEmail(email);

        if(user == null){
            return false;
        }

        if(userId != null && user.getId() == userId.intValue()){
            return false;
        }

        return true;
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable("id") int id){

        userService.updateUser(user,id);

        Map<String,String> response = new HashMap<>();

        response.put("message","User updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);

        Map<String,String> response = new HashMap<>();

        response.put("message","User deleted successfully");

        return ResponseEntity.ok(response);
    }
}
