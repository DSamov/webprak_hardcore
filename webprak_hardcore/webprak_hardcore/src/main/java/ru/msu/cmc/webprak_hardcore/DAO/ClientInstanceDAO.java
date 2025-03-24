package ru.msu.cmc.webprak_hardcore.DAO;

import ru.msu.cmc.webprak_hardcore.models.ClientInstance;
import ru.msu.cmc.webprak_hardcore.models.ClientInstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak_hardcore.models.ClientInstance;

import java.util.List;

@Repository
public class ClientInstanceDAO extends CommonDAO<ClientInstance, ClientInstanceID> {

    public ClientInstanceDAO() {
        super(ClientInstance.class);
    }
}