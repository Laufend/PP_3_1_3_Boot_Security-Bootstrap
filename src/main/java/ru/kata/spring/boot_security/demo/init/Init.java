package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {

    private final UserService userService;
    private final RoleDAO roleDAO;

    @Autowired
    public Init(UserService userService, RoleDAO roleDAO) {
        this.userService = userService;
        this.roleDAO = roleDAO;
    }

    @PostConstruct
    public void addUserDB() {
        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        Role roleA = new Role("ROLE_ADMIN");
        Role roleU = new Role("ROLE_USER");
        roleDAO.addRole(roleA);
        roleDAO.addRole(roleU);
        roleAdmin.add(roleA);
        roleAdmin.add(roleU);
        roleUser.add(roleU);
        User user1 = new User("Admin","Ivanovich", 23, "Sales", 500, "admin", roleAdmin);
        User user2 = new User("User", "Userovoch", 45, "IT", 1000, "user", roleUser);
        userService.addUser(user1);
        userService.addUser(user2);
    }
}

