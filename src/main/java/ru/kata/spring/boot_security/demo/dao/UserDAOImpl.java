package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUserList() {
        return entityManager
                .createQuery("select distinct user from User user join fetch user.roles roles order by user.id", User.class).getResultList();
    }

    @Override
    public void addUser(User user) { entityManager.persist(user); }

    @Override
    public void removeUser(int id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User updateUser(int id, User updated) {
        User user = entityManager.find(User.class, id);
        user.setName(updated.getName());
        user.setSurname(updated.getSurname());
        user.setAge(updated.getAge());
        user.setSpecialization(updated.getSpecialization());
        user.setSalary(updated.getSalary());
        user.setPassword(updated.getPassword());
        user.setRoles(updated.getRoles());
        entityManager.merge(user);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        List<User> list = entityManager.createQuery("select distinct u from User u join fetch u.roles roles where u.name=?1", User.class)
                .setParameter(1, name).getResultList();
        System.out.println(list);
        return list.size() > 0 ? list.get(0) : null;
    }
}
