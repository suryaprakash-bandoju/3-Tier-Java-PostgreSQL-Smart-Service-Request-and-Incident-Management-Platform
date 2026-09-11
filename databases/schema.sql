-- Create Tables
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS service_requests (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id INT REFERENCES categories(id),
    created_by_id INT REFERENCES users(id),
    assigned_to_id INT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    request_id INT REFERENCES service_requests(id),
    user_id INT REFERENCES users(id)
);

-- Seed Data
-- Passwords are BCrypt encoded for 'password123'
INSERT INTO categories (name) VALUES ('Network'), ('Hardware'), ('Software'), ('Access/Permissions');

INSERT INTO users (username, password, full_name, role) VALUES 
('user1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Alice User', 'USER'),
('agent1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Bob Agent', 'AGENT'),
('manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Charlie Manager', 'MANAGER');

INSERT INTO service_requests (title, description, priority, status, category_id, created_by_id) VALUES
('VPN Connection Failed', 'Cannot connect to corporate VPN from home.', 'HIGH', 'OPEN', 1, 1),
('New Monitor Request', 'Need a secondary monitor for dual-screen setup.', 'LOW', 'OPEN', 2, 1);