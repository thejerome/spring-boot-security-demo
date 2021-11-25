package com.example.springbootsecuritydemo.controller;

import com.example.springbootsecuritydemo.entities.UserEntity;
import com.example.springbootsecuritydemo.repo.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/reg")
public class RegController {

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String getReg(@ModelAttribute User user) {
        return "reg";
    }


    @PostMapping
    public String postReg(String username, String password, String repeat, Map<String, Object> model) {
        if (!password.equals(repeat) ) {
            model.put("message", "Password is bad");
            return "reg";
        }

        final UserEntity userWithSameName = userRepo.findByUsername(username);
        if (userWithSameName != null) {
            model.put("message", "Such user already exists");
            return "reg";
        }

        userRepo.save(new UserEntity(username, password));

        return "redirect:/hello";
    }


}
