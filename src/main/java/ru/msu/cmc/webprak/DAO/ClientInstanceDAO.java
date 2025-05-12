package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.ClientInstance;
import ru.msu.cmc.webprak.models.ClientInstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.models.ClientInstance;

import java.util.List;

@Repository
public class ClientInstanceDAO extends CommonDAO<ClientInstance, ClientInstanceID> {
    public ClientInstanceDAO() {
        super(ClientInstance.class);
    }
}