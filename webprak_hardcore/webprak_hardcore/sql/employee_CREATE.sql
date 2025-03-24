DROP TABLE IF EXISTS Employee CASCADE;
CREATE TABLE Employee (
                          id INTEGER NOT NULL,
                          surname TEXT NOT NULL,
                          name TEXT NOT NULL,
                          patron TEXT,
                          address TEXT,
                          education TEXT NOT NULL,
                          work_post TEXT NOT NULL,
                          PRIMARY KEY (id)
);