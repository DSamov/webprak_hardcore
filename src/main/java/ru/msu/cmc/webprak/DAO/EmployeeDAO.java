package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAO extends CommonDAO<Employee, Integer> {

    public EmployeeDAO() {
        super(Employee.class);
    }

    public List<Employee> getByName(Optional<String> surname_, Optional<String> name_, Optional<String> patron_) {
        Query<Employee> query;
        try (Session session = sessionFactory.openSession()) {
            String surname = surname_.orElse("");
            String patron = patron_.orElse("");
            String name = name_.orElse("");

            StringBuilder hql = new StringBuilder("FROM Employee WHERE 1=1");
            if (!surname.isEmpty()) {
                hql.append(" AND surname = :surname");
            }
            if (!name.isEmpty()) {
                hql.append(" AND name = :name");
            }
            if (!patron.isEmpty()) {
                hql.append(" AND patron = :patron");
            }

            query = session.createQuery(hql.toString(), Employee.class);

            if (!surname.isEmpty()) {
                query.setParameter("surname", surname);
            }
            if (!name.isEmpty()) {
                query.setParameter("name", name);
            }
            if (!patron.isEmpty()) {
                query.setParameter("patron", patron);
            }

            return query.list();
        }
    }

    public List<Employee> getByWorkPost(String workPost) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee WHERE work_post = :workPost", Employee.class)
                    .setParameter("workPost", workPost)
                    .list();
        }
    }
}