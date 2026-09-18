-- chef_sphere_schema.sql

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE profiles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(255),
    age INT,
    gender VARCHAR(20),
    height INT,
    weight INT,
    activity_level VARCHAR(50),
    primary_goal VARCHAR(50),
    target_weight INT,
    timeframe VARCHAR(50),
    health_conditions TEXT,
    medications TEXT,
    diet_type VARCHAR(50),
    cuisine_prefs TEXT,
    allergies TEXT,
    restrictions TEXT,
    custom_allergies TEXT,
    skill_level VARCHAR(50),
    cooking_time VARCHAR(50),
    portion_size VARCHAR(50),
    completion_percent INT DEFAULT 0,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE history (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    recipe_name VARCHAR(255),
    cooked_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    cooking_level VARCHAR(50),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE recipes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    nutrition_info TEXT
);