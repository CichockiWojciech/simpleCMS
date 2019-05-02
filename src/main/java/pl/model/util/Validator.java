package pl.model.util;

import javafx.util.Pair;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class Validator {

    public static <T> Pair<Boolean, String> getMsg(Set<ConstraintViolation<T>> constraintViolations){
        StringBuilder msg = new StringBuilder();
        constraintViolations.forEach(constraint -> msg.append(constraint.getMessage()).append("\n"));
        return new Pair<>(false, msg.toString());
    }
}
