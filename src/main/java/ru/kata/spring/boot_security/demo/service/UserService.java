package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

        List<User> getUserList();

        void addUser(User user);

        void removeUser(int id);

        User getUserById(int id);

        User updateUser(int id, User updated);

        Optional<User> getUserByName(String name);

        public List<Role> getAllRoles();

}
