-- 清理表
DELETE FROM normal_user;
DELETE FROM food_item;

-- 插入测试用户数据
INSERT INTO normal_user (user_id, name, password, phone, height, weight, age, gender, isblocked) 
VALUES (1, 'test', 'password123', '13800138000', 175, 65, 25, 'M', 0);

-- 插入测试食物数据
INSERT INTO food_item (food_id, name, calories, protein, fat, carbohydrate) 
VALUES (1, '苹果', 52, 0.3, 0.2, 13.8); 