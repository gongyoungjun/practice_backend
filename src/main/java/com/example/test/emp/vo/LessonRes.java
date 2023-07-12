package com.example.test.emp.vo;

import lombok.Data;

import java.util.List;

@Data
public class LessonRes {
    private String code;
    private List<Lesson> lessonList;

    public LessonRes(String code, List<Lesson> lessonList) {
        this.code = code;
        this.lessonList = lessonList;
    }
}
