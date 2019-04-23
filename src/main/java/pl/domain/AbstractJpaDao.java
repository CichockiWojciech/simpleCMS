package pl.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractJpaDao< T extends Serializable> {
    private Class< T > clazz;

    @PersistenceContext
    private EntityManager entityManager;

    public AbstractJpaDao( Class< T > clazzToSet, EntityManager entityManager) {
        this.clazz = clazzToSet;
        this.entityManager = entityManager;
    }

    public Optional<T> findOne( Long id ){
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    public Optional<T> findOne(String jpgl, Map<String, String> args){
        Query query = entityManager.createQuery(jpgl, clazz);
        Optional.ofNullable(args).ifPresent((useless) -> args.forEach(query::setParameter));
        try {
            return Optional.of((T) query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    public List< T > findAll(){
        return entityManager.createQuery( "from " + clazz.getName(), clazz)
                .getResultList();
    }

    public List< T > findAll(String jpgl, Map<String, String> args){
        Query query = entityManager.createQuery(jpgl, clazz);
        Optional.ofNullable(args).ifPresent((useless) -> args.forEach(query::setParameter));
//        if(args != null)
//            args.forEach(query::setParameter);
        return query.getResultList();
    }

    public void save( T entity ){
        executeInsideTransaction(entityManager -> entityManager.persist(entity));
    }

    public void update( T entity ){
        executeInsideTransaction(entityManager -> entityManager.merge(entity));
    }

    public void delete( T entity ){
        executeInsideTransaction(entityManager -> entityManager.remove(entity));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
