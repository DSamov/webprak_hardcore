DROP TABLE IF EXISTS ClientInstances CASCADE;
CREATE TABLE ClientInstances (
                                 id INTEGER NOT NULL,
                                 phone character(11) NOT NULL,
                                 email TEXT,
                                 FOREIGN KEY (id) REFERENCES ClientsData ON DELETE CASCADE
);