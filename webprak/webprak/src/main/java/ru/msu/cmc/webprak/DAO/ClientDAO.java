package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDAO extends CommonDAO<Client, Integer> {

    public ClientDAO() {
        super(Client.class);
    }

    public List<Client> getByName(Optional<String> surname_, Optional<String> name_, Optional<String> patron_) {
        Query<Client> query;
        try (Session session = sessionFactory.openSession()) {
            String surname = surname_.orElse("");
            String patron = patron_.orElse("");
            String name = name_.orElse("");

            StringBuilder hql = new StringBuilder("FROM Client WHERE 1=1");
            if (!surname.isEmpty()) {
                hql.append(" AND surname = :surname");
            }
            if (!name.isEmpty()) {
                hql.append(" AND name = :name");
            }
            if (!patron.isEmpty()) {
                hql.append(" AND patron = :patron");
            }

            query = session.createQuery(hql.toString(), Client.class);

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
}