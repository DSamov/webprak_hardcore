DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients (
                         id SERIAL,
                         surname TEXT,
                         name TEXT NOT NULL,
                         patron TEXT,
                         PRIMARY KEY (id)
);