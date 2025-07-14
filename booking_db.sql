-- User Table
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birthday DATE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Account Table
CREATE TABLE Accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(12, 2) DEFAULT 0.00,
    opened_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Transaction Table
CREATE TABLE Transactions (
    id SERIAL PRIMARY KEY,
    from_account_id INTEGER,
    to_account_id INTEGER,
    amount DECIMAL(12, 2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_account_id) REFERENCES Accounts(id),
    FOREIGN KEY (to_account_id) REFERENCES Accounts(id)
);
