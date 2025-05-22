package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Instance;
import ru.msu.cmc.webprak.models.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServiceDAO extends CommonDAO<Service, Integer> {

    public ServiceDAO() {
        super(Service.class);
    }

    public List<Service> getByName(Optional<String> name_) {
        try (Session session = sessionFactory.openSession()) {
            String name = name_.orElse(null);

            if (name == null || name.isEmpty()) {
                return session.createQuery("FROM Service", Service.class).list();
            }

            Query<Service> query = session.createQuery(
                    "FROM Service WHERE name = :name", Service.class);
            query.setParameter("name", name);
            return query.list();
        }
    }
}