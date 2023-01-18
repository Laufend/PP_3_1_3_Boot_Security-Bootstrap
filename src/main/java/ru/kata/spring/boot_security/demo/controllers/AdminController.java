package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        List<User> allUsers = userService.getUserList();
        User user = (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", allUsers);
        model.addAttribute("newUser", new User());
        model.addAttribute("user", user);
        return "admin_panel";
    }

    @GetMapping("/add_form")
    public String addNewUser(ModelMap model) {
        User user = (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "new";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.addUser(user);
        return showAllUsers(model);
    }

    @GetMapping("/delete_form")
    public String getDeleteForm(@RequestParam("id") int id, ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> allUsers = userService.getUserList();
        model.addAttribute("user", user);
        model.addAttribute("users", allUsers);
        model.addAttribute("userDelete", userService.getUserById(id));
        return "delete_form";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, ModelMap model) {
        User user = (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> allUsers = userService.getUserList();
        model.addAttribute("user", user);
        model.addAttribute("users", allUsers);
        model.addAttribute("userUpdate", userService.getUserById(id));
        return "edit";
    }

    @GetMapping(value = "/update")
    public String updateUser(@ModelAttribute("userUpdate") User user, ModelMap model) {
        userService.updateUser(user.getId(), user);
        return showAllUsers(model);
    }
}
