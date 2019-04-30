package pl.model.domain;

import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

public class UserDAO extends AbstractJpaDAO<User> {

    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    private Pair<Boolean, String> unique(User user){
        Optional<User> u = this.findOne("SELECT u FROM User u " +
                        "WHERE u.login = :login OR u.email = :email",
                ImmutableMap.of("login", user.getLogin(), "email", user.getEmail()));

        if(u.isPresent())
            return new Pair<>(false, "login lub email zajety");
        else
            return new Pair<>(true, "");
    }

    public Pair<Boolean, String> isValid(User user){
        Set<ConstraintViolation<User>> constraintViolations = super.getValidator().validate(user);
        if(constraintViolations.isEmpty())
            return unique(user);
        return new Pair<>(false, "nieprawidłowo wypełniony formularz");
    }
}
