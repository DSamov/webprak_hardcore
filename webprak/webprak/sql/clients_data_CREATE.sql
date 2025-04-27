DROP TABLE IF EXISTS ClientsData CASCADE;
CREATE TABLE ClientsData (
                            client_id INTEGER NOT NULL,
                            id SERIAL,
                            person TEXT NOT NULL,
                            address TEXT,
                            PRIMARY KEY (id),
                            FOREIGN KEY (client_id) REFERENCES Clients ON DELETE CASCADE
);