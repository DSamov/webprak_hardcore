package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Instance;
import ru.msu.cmc.webprak.models.InstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

@Repository
public class InstanceDAO extends CommonDAO<Instance, InstanceID> {

    public InstanceDAO() {
        super(Instance.class);
    }

    public List<Instance> getByEmployeeId(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE employee.id = :id", Instance.class);
            query.setParameter("id", id);
            return query.list();
        }
    }

    public List<Instance> getByClientId(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE clients.id = :id", Instance.class);
            query.setParameter("id", id);
            return query.list();
        }
    }

    public List<Instance> getByService(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE services.id = :servid", Instance.class);
            query.setParameter("servid", id);
            return query.list();
        }
    }

    public List<Instance> getByService(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE services.name = :servName", Instance.class);
            query.setParameter("servName", name);
            return query.list();
        }
    }

    public List<Instance> getByStartDate(LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE start = :startDate", Instance.class);
            query.setParameter("startDate", Date.valueOf(date)); // Преобразование LocalDate в java.sql.Date
            return query.list();
        }
    }

    public List<Instance> getByFinishDate(LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE finish = :finishDate", Instance.class);
            query.setParameter("finishDate", date != null ? Date.valueOf(date) : null);
            return query.list();
        }
    }

    public List<Instance> getByStartDateRange(LocalDate start, LocalDate finish) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE start BETWEEN :startDate AND :finishDate", Instance.class);
            query.setParameter("startDate", Date.valueOf(start));
            query.setParameter("finishDate", Date.valueOf(finish));
            return query.list();
        }
    }

    public List<Instance> getByFinishDateRange(LocalDate start, LocalDate finish) {
        try (Session session = sessionFactory.openSession()) {
            Query<Instance> query = session.createQuery(
                    "FROM Instance WHERE finish BETWEEN :startDate AND :finishDate", Instance.class);
            query.setParameter("startDate", Date.valueOf(start));
            query.setParameter("finishDate", Date.valueOf(finish));
            return query.list();
        }
    }
}