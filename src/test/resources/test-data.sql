-- 插入测试用户
INSERT INTO normal_user (name, password, weight, age, height, phone, isblocked) 
VALUES ('testUser', 'password123', 70, 25, 175, '13800138000', 0);

-- 插入测试食物
INSERT INTO food_item (name, type, calories, fat, protein, carbohydrates) 
VALUES ('苹果', '水果', 52, 0.2, 0.3, 13.8);

-- 插入测试运动项目
INSERT INTO exercise_item (name, calories_per_hour) 
VALUES ('跑步', 400);

-- 插入测试运动记录
INSERT INTO exercise_record (exercise_id, user_id, date, duration, burned_caloris) 
VALUES (1, 1, '2024-01-01 10:00:00', '30', 200); 