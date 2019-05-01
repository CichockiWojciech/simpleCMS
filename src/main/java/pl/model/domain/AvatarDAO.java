package pl.model.domain;

import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class AvatarDAO extends AbstractJpaDAO<Avatar> {
    public AvatarDAO(EntityManager entityManager) {
        super(Avatar.class, entityManager);
    }

    @Override
    public Pair<Boolean, String> isValid(Avatar avatar){
        Set<ConstraintViolation<Avatar>> constraintViolations = super.getValidator().validate(avatar);
        if(constraintViolations.isEmpty())
            return new Pair<>(true, "");
        StringBuilder msg = new StringBuilder();
        constraintViolations.forEach(constraint -> msg.append(constraint.getMessage()));
        return new Pair<>(false, msg.toString());
    }
}
