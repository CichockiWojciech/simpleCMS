package pl.domain;

import javax.persistence.EntityManager;

public class UserDAO extends AbstractJpaDao<User> {

    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }
}
