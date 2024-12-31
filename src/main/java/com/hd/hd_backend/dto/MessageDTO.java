package com.hd.hd_backend.dto;

import com.hd.hd_backend.utils.JsonUtils;

public abstract class MessageDTO {

    public enum MessageCode {
        success(200),
        less_parameter(400),
        not_found(404),
        not_login(405);
        private final Integer value;

        MessageCode(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }


    private MessageCode code;

    public String to_json()
    {
        return "{\"code\":\"" + code.getValue() + "\"+\"data\":"+JsonUtils.toJson(this.getData())+"}";
    }
    public MessageCode getCode() {
        return code;
    }
    public void setCode(MessageCode code) {
        this.code = code;

    }
    abstract Object getData();
    abstract void setData(Object data);

    public String toString() {
        return to_json();
    }
}
