package com.example.test.emp.vo;

import lombok.Data;

@Data
public class LessonException extends RuntimeException{


    public LessonException() {

    }

    public LessonException(String message) {
        super(message);
    }
}
