package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private RoleDAO roleDAO;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private void checkRolesForUser(User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleDAO.getRole(role.getRole()));
        }
        user.setRoles(roles);
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return userDAO.getUserList();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkRolesForUser(user);
        userDAO.addUser(user);
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    @Override
    public void removeUser(int id) {
        userDAO.removeUser(id);
    }

    @Transactional
    @Override
    public User updateUser(int id, User updated) {
        updated.setPassword(passwordEncoder.encode(updated.getPassword()));
        checkRolesForUser(updated);
        return userDAO.updateUser(id, updated);
    }

    @Transactional
    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

}

//    @Transactional
//    @Override
//    public List<User> getUserList() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public List<Role> getAllRoles() { return roleRepository.findAll(); }
//
//    @Transactional
//    @Override
//    public void addUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        checkRolesForUser(user);
//        userRepository.save(user);
//    }
//
//    private void checkRolesForUser(User user) {
//        Set<Role> roles = new HashSet<>();
//        for (Role role : user.getRoles()) {
//            roles.add(roleRepository.getRoleByRole(role.getRole()));
//        }
//        user.setRoles(roles);
//    }
//
//    @Transactional
//    @Override
//    public User getUserById(int id) {
//        return userRepository.findById(id).get();
//    }
//
//    @Transactional
//    @Override
//    public void removeUser(int id) {
//        userRepository.deleteById(id);
//    }
//
//    @Transactional
//    @Override
//    public User updateUser(int id, User updated) {
//        updated.setId(id);
//        updated.setPassword(passwordEncoder.encode(updated.getPassword()));
//        checkRolesForUser(updated);
//        return userRepository.save(updated);
//    }
//
//    @Transactional
//    @Override
//    public Optional<User> getUserByName(String name) {
//        return userRepository.findByUsername(name);
//    }

