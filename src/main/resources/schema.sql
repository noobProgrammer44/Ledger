CREATE TABLE IF NOT EXISTS tasks(
    -- SERIAL PRIMARY KEY makes id a number that the database fills in for you (1, 2, 3...), so the database now does the job of our nextId counter.
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    done  BOOLEAN NOT NULL DEFAULT FALSE
);