package pl.model.domain;

import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

public class ContentDAO extends AbstractJpaDAO<Content> {

    public ContentDAO(EntityManager entityManager) {
        super(Content.class, entityManager);
    }

    private Pair<Boolean, String> unique(Content content){
        Optional<Content> c = this.findOne("SELECT c FROM Content c " +
                        "WHERE c.title = :title",
                ImmutableMap.of("title", content.getTitle()));

        if(c.isPresent())
            return new Pair<>(false, "tytul jest zajety");
        return new Pair<>(true, "");
    }

    public Pair<Boolean, String> isValid(Content content){
        Set<ConstraintViolation<Content>> constraintViolations = super.getValidator().validate(content);
        if(constraintViolations.isEmpty())
            return unique(content);
        return new Pair<>(false, "nieprawidłowo wypełniony formularz");
    }
}
