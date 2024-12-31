package com.hd.hd_backend.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> String toJsonMsg(int code, T data ,String msg_type)
    {
        if (code%2==0)
        {
            return "{\"code\":" + code +",\"status\":200"+ ",\""+msg_type+"\":"+ toJson(data)+"}";
        }
        else {
            return "{\"code\":" + code+ ",\"status\":400" + ",\""+msg_type+"\":"+ toJson(data)+"}";
        }

    }

}