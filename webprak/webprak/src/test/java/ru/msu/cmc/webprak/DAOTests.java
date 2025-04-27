package ru.msu.cmc.webprak;

import org.springframework.context.annotation.ComponentScan;
import ru.msu.cmc.webprak.DAO.EmployeeDAO;
import ru.msu.cmc.webprak.DAO.ClientDAO;
import ru.msu.cmc.webprak.DAO.InstanceDAO;
import ru.msu.cmc.webprak.DAO.ServiceDAO;
import ru.msu.cmc.webprak.models.Employee;
import ru.msu.cmc.webprak.models.Client;
import ru.msu.cmc.webprak.models.Instance;
import ru.msu.cmc.webprak.models.Service;

import ru.msu.cmc.webprak.config.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class DAOTests {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private InstanceDAO instanceDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    @Test
    public void testEmployee() {
        Employee emp1 = new Employee();
        emp1.setSurname("Самоваров");
        assertEquals("Самоваров", emp1.getSurname());

        List<Employee> empList = employeeDAO.getByName(Optional.of("Самоваров"), Optional.of("Дмитрий"), Optional.of("Олегович"));
        assertNotNull(empList);
        assertFalse(empList.isEmpty());
        Employee emp2 = empList.get(0);
        assertNotNull(emp2);

        List<Employee> lemp1 = employeeDAO.getByName(Optional.of("Самоваров"), Optional.empty(), Optional.empty());
        assertEquals(2, lemp1.size());

        List<Employee> lemp2 = employeeDAO.getByWorkPost("Параюрист");
        assertEquals(1, lemp2.size());
    }

    @Test
    public void testCommon() {
        Employee emp1 = employeeDAO.getById(1);
        assertNotNull(emp1);

        Employee emp2 = employeeDAO.getById(13);
        assertNull(emp2);

        assertEquals(5, employeeDAO.getAll().size());

        Service serv = new Service(null, "Разрешение семейных споров", 20000.0F);
        serviceDAO.save(serv);
        serviceDAO.deleteById(serv.getId());
        assertNull(serviceDAO.getById(serv.getId()));

        Employee nemp1 = new Employee();
        nemp1.setId(null);
        nemp1.setSurname("Иванов");
        nemp1.setName("Иван");
        nemp1.setPatron("Иванович");
        nemp1.setAddress("Улица Пушкина, дом Калатушкина");
        nemp1.setEducation("ЮрФак МГУ");
        nemp1.setWork_post("Штатный юрист");
        employeeDAO.save(nemp1);
        Employee nemp2 = employeeDAO.getById(nemp1.getId());
        assertNotNull(nemp2);

        assertEquals("Иван", nemp2.getName());

        int mySize = employeeDAO.getByWorkPost("Юрист").size() + 1;
        nemp2.setWork_post("Юрист");
        employeeDAO.update(nemp2);
        assertEquals(mySize, employeeDAO.getByWorkPost("Юрист").size());
        employeeDAO.deleteOne(nemp1);
    }

    @Test
    public void testClients() {
        List<Client> clients = clientDAO.getByName(Optional.of("Иванов"), Optional.of("Иван"), Optional.empty());
        assertEquals(1, clients.size());
    }

    @Test
    public void testInstances() {

        List<Instance> inst = instanceDAO.getByEmployeeId(3);
        assertEquals(1, inst.size());

        inst = instanceDAO.getByClientId(10);
        assertEquals(0, inst.size());

        inst = instanceDAO.getByService("Правовая поддержка");
        assertEquals(1, inst.size());

        inst = instanceDAO.getByStartDate(LocalDate.of(2024, 10, 9));
        assertEquals(1, inst.size());
        inst = instanceDAO.getByFinishDate(LocalDate.of(2025, 12, 2));
        assertEquals(0, inst.size());
        inst = instanceDAO.getByStartDateRange(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 6, 1));
        assertEquals(2, inst.size());
        inst = instanceDAO.getByFinishDateRange(LocalDate.of(2024, 11, 1), LocalDate.of(2024, 12, 31));
        assertEquals(1, inst.size());
    }
}