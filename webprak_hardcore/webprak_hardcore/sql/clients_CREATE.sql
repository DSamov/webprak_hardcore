DROP TABLE IF EXISTS Clients CASCADE;
CREATE TABLE Clients (
                         id INTEGER NOT NULL,
                         surname TEXT,
                         name TEXT NOT NULL,
                         patron TEXT,
                         PRIMARY KEY (id)
);