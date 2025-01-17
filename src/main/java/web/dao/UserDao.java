package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    public EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}
