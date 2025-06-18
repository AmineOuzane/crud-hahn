-- Table for AppUser
CREATE TABLE app_user (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    confirm_password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

-- Table for Product
CREATE TABLE product (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE NOT NULL,
    created_at DATETIME NOT NULL,
    app_user_id VARCHAR(36) NOT NULL,
    CONSTRAINT fk_product_user FOREIGN KEY (app_user_id) REFERENCES app_user(id) ON DELETE CASCADE
);