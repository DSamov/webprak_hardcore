package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.ClientData;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDataDAO extends CommonDAO<ClientData, Integer> {

    public ClientDataDAO() {
        super(ClientData.class);
    }

    public List<ClientData> getByPerson(String person) {
        Query<ClientData> query;
        try (Session session = sessionFactory.openSession()) {
        query = session.createQuery("FROM ClientData WHERE person = :person", ClientData.class);
        query.setParameter("person", person);
        }
        return query.list();
    }

    public List<ClientData> getByClientID(Long id) {
        Query<ClientData> query;
        try (Session session = sessionFactory.openSession()) {
            query = session.createQuery("FROM ClientData WHERE clients = :id", ClientData.class);
            query.setParameter("id", id);
        }
        return query.list();
    }
}