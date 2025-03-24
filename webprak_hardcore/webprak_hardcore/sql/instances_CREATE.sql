DROP TABLE IF EXISTS Instances CASCADE;
CREATE TABLE Instances (
                           client_id INTEGER NOT NULL,
                           employee_id INTEGER NOT NULL,
                           service_id INTEGER NOT NULL,
                           start_date DATE NOT NULL,
                           finish_date DATE,
                           FOREIGN KEY (client_id) REFERENCES Clients ON DELETE CASCADE,
                           FOREIGN KEY (employee_id) REFERENCES Employee ON DELETE CASCADE,
                           FOREIGN KEY (service_id) REFERENCES Services ON DELETE CASCADE
);