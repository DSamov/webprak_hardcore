DROP TABLE IF EXISTS Employee CASCADE;
CREATE TABLE Employee (
                          id SERIAL,
                          surname TEXT NOT NULL,
                          name TEXT NOT NULL,
                          patron TEXT,
                          address TEXT,
                          email TEXT,
                          phone character (11) NOT NULL,
                          education TEXT NOT NULL,
                          work_post TEXT NOT NULL,
                          PRIMARY KEY (id)
);