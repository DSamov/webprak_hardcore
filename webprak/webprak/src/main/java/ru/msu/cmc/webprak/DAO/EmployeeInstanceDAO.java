package ru.msu.cmc.webprak.DAO;

import ru.msu.cmc.webprak.models.EmployeeInstance;
import ru.msu.cmc.webprak.models.EmployeeInstanceID;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprak.models.EmployeeInstance;

import java.util.List;

@Repository
public class EmployeeInstanceDAO extends CommonDAO<EmployeeInstance, EmployeeInstanceID> {

    public EmployeeInstanceDAO() {
        super(EmployeeInstance.class);
    }
}