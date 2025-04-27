DROP TABLE IF EXISTS EmployeeInstances CASCADE;
CREATE TABLE EmployeeInstances (
                              id INTEGER NOT NULL,
                              phone character(11) NOT NULL,
                              email TEXT,
                              FOREIGN KEY (id) REFERENCES Employee ON DELETE CASCADE
);