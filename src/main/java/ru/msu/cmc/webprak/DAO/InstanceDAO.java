package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.Instance;
import ru.msu.cmc.webprak.models.InstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;
import java.util.Optional;

@Repository
public class InstanceDAO extends CommonDAO<Instance, InstanceID> {

    public InstanceDAO() {
        super(Instance.class);
    }

    public List<Instance> getByObjectId(Optional<List<Integer>> employeeIds, Optional<List<Integer>> clientIds, Optional<List<Integer>> serviceIds) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Instance i WHERE 1=1");

            if (clientIds.isPresent() && !clientIds.get().isEmpty()) {
                hql.append(" AND i.clients.id IN :clientIds");
            }
            if (employeeIds.isPresent() && !employeeIds.get().isEmpty()) {
                hql.append(" AND i.employee.id IN :employeeIds");
            }
            if (serviceIds.isPresent() && !serviceIds.get().isEmpty()) {
                hql.append(" AND i.services.id IN :serviceIds");
            }

            Query<Instance> query = session.createQuery(hql.toString(), Instance.class);

            if (clientIds.isPresent() && !clientIds.get().isEmpty()) {
                query.setParameter("clientIds", clientIds.get());
            }
            if (employeeIds.isPresent() && !employeeIds.get().isEmpty()) {
                query.setParameter("employeeIds", employeeIds.get());
            }
            if (serviceIds.isPresent() && !serviceIds.get().isEmpty()) {
                query.setParameter("serviceIds", serviceIds.get());
            }

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