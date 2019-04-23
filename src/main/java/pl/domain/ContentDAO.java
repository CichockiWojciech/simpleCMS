package pl.domain;

import javax.persistence.EntityManager;

public class ContentDAO extends AbstractJpaDao<Content> {

    public ContentDAO(EntityManager entityManager) {
        super(Content.class, entityManager);
    }
}
