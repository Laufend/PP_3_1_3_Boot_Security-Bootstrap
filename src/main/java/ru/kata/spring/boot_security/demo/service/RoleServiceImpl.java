package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public List<Role> getListRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public List<Role> getRolesListById(List<Integer> id) {
        return roleRepository.findAllById(id);
    }

    @Override
    @Transactional
    public Role getRoleById(int id) {
        return roleRepository.getById(id);
    }
}
