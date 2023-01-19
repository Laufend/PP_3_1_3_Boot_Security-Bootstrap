package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allUsersList(ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> allUsers = userService.getUserList();
        model.addAttribute("user", user);
        model.addAttribute("users", allUsers);
        return "admin_panel";
    }
    @GetMapping( "/add_form")
    public String getAddingForm(ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        return "new";
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
    @GetMapping("/delete")
    public String deleteUser(@ModelAttribute User user) {
        userService.removeUser(user.getId());
        return "redirect:/admin";
    }
    @PostMapping("/add")
    public String saveUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/edit")
    public String editForm(@RequestParam("id") int id, ModelMap model) {
        User user = (User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<User> allUsers = userService.getUserList();
        model.addAttribute("user", user);
        model.addAttribute("users", allUsers);
        model.addAttribute("userUpdate", userService.getUserById(id));
        return "edit";
    }
    @GetMapping("/update")
    public String update(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
