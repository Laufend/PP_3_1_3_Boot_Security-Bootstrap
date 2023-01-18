package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() { return roleRepository.findAll(); }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        checkRolesForUser(user);
        userRepository.save(user);
    }

    private void checkRolesForUser(User user) {
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleRepository.getRoleByRole(role.getRole()));
        }
        user.setRoles(roles);
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User updateUser(int id, User updated) {
        updated.setId(id);
        updated.setPassword(passwordEncoder.encode(updated.getPassword()));
        checkRolesForUser(updated);
        return userRepository.save(updated);
    }

    @Transactional
    @Override
    public Optional<User> getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

}
