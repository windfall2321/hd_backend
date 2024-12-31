package com.hd.hd_backend.dto;

public class ExerciseRecordMessageDTO extends MessageDTO {
    ExerciseRecordDTO record;

    @Override
    Object getData() {
        return record;
    }

    @Override
    void setData(Object data) {
        record = (ExerciseRecordDTO) data;
    }

}
