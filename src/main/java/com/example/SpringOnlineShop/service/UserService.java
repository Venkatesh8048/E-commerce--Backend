package com.example.SpringOnlineShop.service;

import com.example.SpringOnlineShop.entity.User;
import com.example.SpringOnlineShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(User user){
        String rawPassword = user.getPassword();
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         userRepo.save(user);

        mailService.sendMail(
                user.getEmail(),
                "User Registration",
                "Hello " + user.getUsername() +
                        ",\nUserName: " + user.getUsername() +
                        "\nPassword: " + rawPassword +
                        ",\n\nYou have been successfully added to the system."
        );

    }

    public List<User> getAllUsers(){

        return userRepo.findAll();
    }

    public void updateUser(User user,int id){
        User u = userRepo.findById(id).get();

        String rawPassword = user.getPassword();

        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        u.setPhone(user.getPhone());
        u.setAddress(user.getAddress());
        u.setRole(user.getRole());

        userRepo.save(u);

        mailService.sendMail(
                user.getEmail(),
                "User Registration",
                "Hello " + user.getUsername() +
                        ",\nUserName: " + user.getUsername() +
                        "\nPassword: " + rawPassword +
                        ",\n\nYou have been successfully updated."
        );

    }

    public void deleteUser(int id){
        userRepo.deleteById(id);
    }


}
