package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.Role;


public interface RoleDAO {

    Role getRole(String nameRole);

    void addRole(Role role);

}
