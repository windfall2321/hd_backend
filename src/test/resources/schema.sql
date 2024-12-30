-- 创建用户表
CREATE TABLE IF NOT EXISTS normal_user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    profile_picture VARCHAR(200),
    weight INT,
    age INT,
    height INT,
    gender INT,
    activity_factor INT,
    isblocked INT DEFAULT 0,
    phone VARCHAR(20)
);

-- 创建食物表
CREATE TABLE IF NOT EXISTS food_item (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50),
    calories INT,
    fat DOUBLE,
    protein DOUBLE,
    carbohydrates DOUBLE,
    dietary_fiber DOUBLE,
    potassium DOUBLE,
    sodium DOUBLE
);

-- 创建食物记录表
CREATE TABLE IF NOT EXISTS food_record (
    food_record_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    food_id INT,
    record_time TIMESTAMP,
    food_weight DOUBLE,
    calories INT,
    fat DOUBLE,
    protein DOUBLE,
    carbohydrates DOUBLE,
    sodium DOUBLE,
    potassium DOUBLE,
    dietary_fiber DOUBLE
);

-- 创建运动项目表
CREATE TABLE IF NOT EXISTS exercise_item (
    exercise_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    calories_per_hour INT
);

-- 创建运动记录表
CREATE TABLE IF NOT EXISTS exercise_record (
    exercise_record_id INT AUTO_INCREMENT PRIMARY KEY,
    exercise_id INT,
    user_id INT,
    date DATETIME,
    duration VARCHAR(20),
    burned_caloris INT
); 