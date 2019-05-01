package pl.model.domain;

import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

public class UserDAO extends AbstractJpaDAO<User> {

    public UserDAO(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    private Pair<Boolean, String> unique(User user){
        List<User> u = this.findAll("SELECT u FROM User u " +
                        "WHERE u.login = :login OR u.email = :email",
                ImmutableMap.of("login", user.getLogin(), "email", user.getEmail()));

        if(!u.isEmpty())
            return new Pair<>(false, "login lub email zajety");
        return new Pair<>(true, "");
    }

    private Pair<Boolean, String> uniqueExcept(User user){
        List<User> u = this.findAll("SELECT u FROM User u " +
                        "WHERE u.login = :login OR u.email = :email",
                ImmutableMap.of("login", user.getLogin(), "email", user.getEmail()));
        if(u.size() > 1)
            return new Pair<>(false, "login lub email zajęty");
        if(!u.isEmpty()){
            this.refresh(user);
            if(u.get(0) == user)
                return new Pair<>(true, "");
            return new Pair<>(false, "login lub email zajęty");
        }
        return new Pair<>(true, "");
    }

    public Pair<Boolean, String> isValid(User user){
        Set<ConstraintViolation<User>> constraintViolations = super.getValidator().validate(user);
        if(constraintViolations.isEmpty())
            return unique(user);
        return new Pair<>(false, "nieprawidłowo wypełniony formularz");
    }

    public Pair<Boolean, String> isValidOnUpdate(User user){
        Set<ConstraintViolation<User>> constraintViolations = super.getValidator().validate(user);
        if(constraintViolations.isEmpty())
            return uniqueExcept(user);
        StringBuilder msg = new StringBuilder();
        constraintViolations.forEach(constraint -> msg.append(constraint.getMessage()));
        return new Pair<>(false, msg.toString());
    }
}
