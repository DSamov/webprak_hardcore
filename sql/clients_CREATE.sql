DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients (
                         id SERIAL,
                         surname TEXT,
                         name TEXT NOT NULL,
                         patron TEXT,
                         address TEXT,
                         email TEXT,
                         phone character (11) NOT NULL,
                         PRIMARY KEY (id)
);