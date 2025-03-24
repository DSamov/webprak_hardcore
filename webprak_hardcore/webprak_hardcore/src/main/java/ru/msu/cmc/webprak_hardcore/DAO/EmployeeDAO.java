package ru.msu.cmc.webprak_hardcore.DAO;

import ru.msu.cmc.webprak_hardcore.models.Employee;
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
        Session session = getCurrentSession();
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

        Query<Employee> query = session.createQuery(hql.toString(), Employee.class);

        if (!surname.isEmpty()) {
            query.setParameter("surname", surname);
        }
        if (!name.isEmpty()) {
            query.setParameter("name", name);
        }
        if (!patron.isEmpty()) {
            query.setParameter("patron", patron);
        }

        return query != null ? query.list() : null;
    }

    public List<Employee> getByWorkPost(String workPost) {
        Session session = getCurrentSession();
        Query<Employee> query = session.createQuery("FROM Employee WHERE work_post = :workPost", Employee.class);
        query.setParameter("post", workPost);
        return query.list();
    }
}