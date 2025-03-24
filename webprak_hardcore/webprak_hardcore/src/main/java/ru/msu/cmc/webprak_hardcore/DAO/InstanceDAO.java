package ru.msu.cmc.webprak_hardcore.DAO;

import ru.msu.cmc.webprak_hardcore.models.Instance;
import ru.msu.cmc.webprak_hardcore.models.InstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class InstanceDAO extends CommonDAO<Instance, InstanceID> {

    public InstanceDAO() {
        super(Instance.class);
    }

    public List<Instance> getByEmployeeId(Integer id) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.employee.id = :id", Instance.class);
        query.setParameter("id", id);
        return query.list();
    }

    public List<Instance> getByClientId(Integer id) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.clients.id = :id", Instance.class);
        query.setParameter("id", id);
        return query.list();
    }

    public List<Instance> getByService(Integer id) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.services.id = :servid", Instance.class);
        query.setParameter("servid", id);
        return query.list();
    }

    public List<Instance> getByService(String name) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.services.name = :servName", Instance.class);
        query.setParameter("servName", name);
        return query.list();
    }

    public List<Instance> getByStartDate(LocalDate date) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.start = :startDate", Instance.class);
        query.setParameter("startDate", date);
        return query.list();
    }

    public List<Instance> getByFinishDate(LocalDate date) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.finish = :finishDate", Instance.class);
        query.setParameter("finishDate", date);
        return query.list();
    }

    public List<Instance> getByStartDateRange(LocalDate start, LocalDate finish) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.start BETWEEN :startDate AND :finishDate", Instance.class);
        query.setParameter("startDate", start);
        query.setParameter("finishDate", finish);
        return query.list();
    }

    public List<Instance> getByFinishDateRange(LocalDate start, LocalDate finish) {
        Session session = getCurrentSession();
        Query<Instance> query = session.createQuery(
                "FROM Instance WHERE Instance.finish BETWEEN :startDate AND :finishDate", Instance.class);
        query.setParameter("startDate", start);
        query.setParameter("finishDate", finish);
        return query.list();
    }
}