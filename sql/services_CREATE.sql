DROP TABLE IF EXISTS Services CASCADE;
CREATE TABLE Services (
                          id SERIAL,
                          name TEXT NOT NULL,
                          cost FLOAT,
                          PRIMARY KEY (id)
);