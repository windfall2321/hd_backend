package com.hd.hd_backend.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {
    
    // 用于测试的简单静态内部类
    static class TestObject {
        private String name;
        private int value;
        
        public TestObject() {}
        
        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }

    @Test
    void testToJson() {
        // 测试正常对象转JSON
        TestObject testObj = new TestObject("test", 100);
        String json = JsonUtils.toJson(testObj);
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"test\""));
        assertTrue(json.contains("\"value\":100"));
        
        // 测试空对象
        TestObject emptyObj = new TestObject(null, 0);
        String emptyJson = JsonUtils.toJson(emptyObj);
        assertNotNull(emptyJson);
        assertTrue(emptyJson.contains("\"name\":null"));
        assertTrue(emptyJson.contains("\"value\":0"));
        
        // 测试null对象
        assertNull(JsonUtils.toJson(null));
    }

    @Test
    void testFromJson() {
        // 测试正常JSON转对象
        String json = "{\"name\":\"test\",\"value\":100}";
        TestObject obj = JsonUtils.fromJson(json, TestObject.class);
        assertNotNull(obj);
        assertEquals("test", obj.getName());
        assertEquals(100, obj.getValue());
        
        // 测试包含null值的JSON
        String jsonWithNull = "{\"name\":null,\"value\":0}";
        TestObject objWithNull = JsonUtils.fromJson(jsonWithNull, TestObject.class);
        assertNotNull(objWithNull);
        assertNull(objWithNull.getName());
        assertEquals(0, objWithNull.getValue());
        
        // 测试无效JSON
        assertNull(JsonUtils.fromJson("invalid json", TestObject.class));
        
        // 测试null输入
        assertNull(JsonUtils.fromJson(null, TestObject.class));
    }
} 