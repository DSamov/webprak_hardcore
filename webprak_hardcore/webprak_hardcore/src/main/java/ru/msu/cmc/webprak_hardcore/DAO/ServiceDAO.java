package ru.msu.cmc.webprak_hardcore.DAO;

import ru.msu.cmc.webprak_hardcore.models.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceDAO extends CommonDAO<Service, Integer> {

    public ServiceDAO() {
        super(Service.class);
    }
}