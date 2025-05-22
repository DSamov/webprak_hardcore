package ru.msu.cmc.webprak;

import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl = "http://localhost:8080";

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement findElement(By path) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(path));
    }

    private void click(By path) {
        wait.until(ExpectedConditions.elementToBeClickable(path)).click();
    }

    @Test
    void testHomePage() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.titleIs("Юридическая фирма"));

        // Проверка основных элементов на главной странице
        assertTrue(driver.findElement(By.tagName("h1")).getText()
                .contains("Юридическая фирма"));
        assertTrue(driver.findElement(By.tagName("p")).getText()
                .contains("Приложение для управления базой данных юр. фирмы."));
    }

    @Test
    void testClientsPageSearch() {
        driver.get(baseUrl);
        click(By.linkText("Клиенты"));
        wait.until(ExpectedConditions.titleIs("Клиенты"));

        // Поиск по фамилии
        findElement(By.id("surname")).sendKeys("Иванов");
        click(By.xpath("//button[text()='Поиск']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 0;
        });

        // Сброс поиска
        click(By.xpath("//button[text()='Сбросить']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 1;
        });
    }

    @Test
    void testAddAndDeleteClient() {
        driver.get(baseUrl);
        click(By.linkText("Клиенты"));
        wait.until(ExpectedConditions.titleIs("Клиенты"));

        int initialClientsCount = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr")).size();

        // Добавление нового клиента
        click(By.xpath("//a[contains(@class, 'btn-success') and contains(text(), 'Добавить клиентов')]"));

        findElement(By.id("surname")).sendKeys("Тестов");
        findElement(By.id("name")).sendKeys("Тест");
        findElement(By.id("patron")).sendKeys("Тестович");
        findElement(By.id("address")).sendKeys("ул. Тестовая, 123");
        findElement(By.id("phone")).sendKeys("89991112233");

        click(By.xpath("//button[text()='Сохранить']"));

        // Проверка, что клиент добавился
        wait.until(d -> {
            int currentClientsCount = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr")).size();
            return currentClientsCount == initialClientsCount + 1;
        });

        // Поиск добавленного клиента
        findElement(By.id("surname")).sendKeys("Тестов");
        click(By.xpath("//button[text()='Поиск']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() == 1;
        });

        // Удаление клиента
        click(By.xpath("//a[contains(@class, 'btn-primary') and text()='Просмотр']"));
        click(By.xpath("//a[contains(@class, 'btn-danger') and text()='Удалить клиента']"));

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Проверка, что клиент удален
        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() == initialClientsCount;
        });
    }

    @Test
    void testEmployeesPageSearch() {
        driver.get(baseUrl);
        click(By.linkText("Служащие"));
        wait.until(ExpectedConditions.titleIs("Служащие"));

        // Поиск по имени
        findElement(By.id("name")).sendKeys("Петр");
        click(By.xpath("//button[text()='Поиск']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 0;
        });

        // Сброс поиска
        click(By.xpath("//button[text()='Сбросить']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 1;
        });
    }

    @Test
    void testAddEmployee() {
        driver.get(baseUrl);
        click(By.linkText("Служащие"));
        wait.until(ExpectedConditions.titleIs("Служащие"));

        int initialEmployeesCount = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr")).size();

        // Добавление нового служащего
        click(By.xpath("//a[contains(@class, 'btn-success') and contains(text(), 'Добавить служащего')]"));

        findElement(By.id("surname")).sendKeys("Служаев");
        findElement(By.id("name")).sendKeys("Сергей");
        findElement(By.id("patron")).sendKeys("Сергеевич");
        findElement(By.id("education")).sendKeys("Юридическое");
        findElement(By.id("work_post")).sendKeys("Юрист");
        findElement(By.id("phone")).sendKeys("89994445566");

        click(By.xpath("//button[text()='Сохранить']"));

        // Проверка, что служащий добавился
        wait.until(d -> {
            int currentEmployeesCount = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr")).size();
            return currentEmployeesCount == initialEmployeesCount + 1;
        });
    }

    @Test
    void testInstancesPageSearch() {
        driver.get(baseUrl);
        click(By.linkText("История услуг"));
        wait.until(ExpectedConditions.titleIs("Экземпляры услуг"));

        // Поиск по клиенту
        findElement(By.id("clientSurname")).sendKeys("Иванов");
        click(By.xpath("//button[text()='Поиск']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 0;
        });

        // Сброс поиска
        click(By.xpath("//button[text()='Сбросить']"));

        wait.until(d -> {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            return rows.size() > 1;
        });
    }

    @Test
    void testAddInstanceWithFixedDate() {
            // 1. Переход на страницу
            driver.get(baseUrl);
            click(By.linkText("История услуг"));
            wait.until(ExpectedConditions.titleIs("Экземпляры услуг"));

            // 2. Получение начального количества записей
            List<WebElement> initialRows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
            int initialInstancesCount = initialRows.size();

            // 3. Переход на форму добавления
            click(By.xpath("//a[contains(@class, 'btn-success') and contains(text(), 'Добавить экземпляр')]"));
            wait.until(ExpectedConditions.titleIs("Добавление экземпляра услуги"));

            // 4. Заполнение формы
            // Клиент
            WebElement clientSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("clientId")));
            Select clientSelect = new Select(clientSelectElement);
            wait.until(d -> clientSelect.getOptions().size() > 1);
            clientSelect.selectByIndex(2);

            // Сотрудник
            WebElement employeeSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("employeeId")));
            Select employeeSelect = new Select(employeeSelectElement);
            wait.until(d -> employeeSelect.getOptions().size() > 1);
            employeeSelect.selectByIndex(3);

            // Услуга
            WebElement serviceSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("serviceId")));
            Select serviceSelect = new Select(serviceSelectElement);
            wait.until(d -> serviceSelect.getOptions().size() > 1);
            serviceSelect.selectByIndex(4);

            // Фиксированная дата вместо текущей
            String fixedDate = "22.05.2025"; // Формат yyyy-MM-dd

            WebElement startDateField = wait.until(ExpectedConditions.elementToBeClickable(By.id("start")));
            startDateField.clear();
            startDateField.sendKeys(fixedDate);

            // 5. Отправка формы
            click(By.xpath("//button[text()='Сохранить']"));

            // 6. Ожидание возврата на страницу списка
            wait.until(ExpectedConditions.titleIs("Экземпляры услуг"));

            // 7. Проверка добавления записи
            wait.until(d -> {
                List<WebElement> currentRows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
                return currentRows.size() == initialInstancesCount + 1;
            });

            findElement(By.id("clientSurname")).sendKeys("Петров");
            findElement(By.id("employeeSurname")).sendKeys("Самоваров");
            findElement(By.id("employeeName")).sendKeys("Иван");

            click(By.xpath("//button[text()='Поиск']"));

            wait.until(d -> {
                List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
                return !rows.isEmpty();
            });

            click(By.xpath("//a[contains(@class, 'btn-danger') and text()='Удалить']"));
            click(By.xpath("//button[text()='Сбросить']"));

            wait.until(d -> {
                List<WebElement> rows = driver.findElements(By.xpath("//table[@class='table table-striped']//tbody/tr"));
                return rows.size() == initialInstancesCount;
            });
    }
}