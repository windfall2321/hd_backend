package com.hd.hd_backend.dto;

public class ErrorMessageDTO extends MessageDTO {
    private String errormessage;

    @Override
    void setData(Object data) {
        this.errormessage = (String) data;
    }
    @Override
    Object getData() {
        return errormessage;
    }
    public ErrorMessageDTO() {}
    public ErrorMessageDTO(MessageCode messageCode, String errormessage) {
        setCode(messageCode);
        setData(errormessage);
    }
}
