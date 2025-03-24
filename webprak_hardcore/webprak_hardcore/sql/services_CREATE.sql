DROP TABLE IF EXISTS Services CASCADE;
CREATE TABLE Services (
                          id INTEGER NOT NULL,
                          name TEXT NOT NULL,
                          cost FLOAT,
                          PRIMARY KEY (id)
);