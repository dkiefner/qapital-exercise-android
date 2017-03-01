CREATE TABLE saving_goal (
    id INTEGER NOT NULL PRIMARY KEY,
    goal_image_url TEXT,
    target_amount REAL,
    current_balance REAL DEFAULT 0 NOT NULL,
    status TEXT,
    name TEXT NOT NULL
    );

CREATE TABLE savings_rule (
    id INTEGER NOT NULL PRIMARY KEY,
    type TEXT,
    amount REAL
    );

CREATE TABLE saving_goal_event (
    id TEXT NOT NULL PRIMARY KEY,
    type TEXT,
    timestamp TEXT,
    message TEXT,
    amount REAL NOT NULL,
    fk_savings_rule INTEGER
    );
