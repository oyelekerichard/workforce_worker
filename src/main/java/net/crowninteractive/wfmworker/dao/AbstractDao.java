/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author osita
 * @param <PK>
 * @param <T>
 */
public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    protected Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    public void persist(T entity) {
        Session sess = getSession();
        sess.getTransaction().begin();
        sess.persist(entity);
        sess.getTransaction().commit();
    }

    public void edit(T entity) {
        Session sess = getSession();
        sess.getTransaction().begin();
        sess.merge(entity);
        sess.refresh(entity);
        sess.getTransaction().commit();
    }

    public void delete(T entity) {
        Session sess = getSession();
        sess.getTransaction().begin();

        sess.delete(sess.merge(entity));
        sess.getTransaction().commit();
    }

    public void deleteList(List<T> entity) {
        EntityManager em = getEntityManager();
        entity.stream().forEach((e) -> {
            em.remove(em.merge(e));
        });
    }

    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
