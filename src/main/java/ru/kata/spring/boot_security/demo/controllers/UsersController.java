package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    @GetMapping({"/", "/index"})
    public String getIndexPage () {
        return "index";
    }

    @GetMapping("/user")
    public String getUserInfo(ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority role : user.getAuthorities()) {
            roles.add(role.getAuthority());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("userinfo", user);
        return "user";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}
