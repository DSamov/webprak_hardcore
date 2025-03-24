DROP TABLE IF EXISTS ClientsData CASCADE;
CREATE TABLE ClientsData (
                            client_id INTEGER NOT NULL,
                            id INTEGER NOT NULL,
                            person TEXT NOT NULL,
                            address TEXT,
                            PRIMARY KEY (id),
                            FOREIGN KEY (client_id) REFERENCES Clients ON DELETE CASCADE
);