package ru.msu.cmc.webprak.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.models.Common;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class CommonDAO<T extends Common<Id>, Id> {
    protected Class<T> entityClass;

    protected SessionFactory sessionFactory;

    public CommonDAO(Class<T> entityClass) { this.entityClass = entityClass; }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public T getById(Id id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(entityClass, (Serializable) id);
        }
    }

    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(entityClass);
            criteria.from(entityClass);
            return session.createQuery(criteria).getResultList();
        }
    }

    public void save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    public void saveCollection(Collection<T> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (T entity : entities) {
                if (entity.getId() == null) {
                    entity.setId(null);
                }
                session.save(entity);
            }
            session.getTransaction().commit();
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    public void deleteOne(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    public void deleteById(Id id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = getById(id);
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}