package com.example.test.api.emp.vo;

import lombok.Data;

import java.util.List;

@Data
public class LessonRes {
    private List<Lesson> lessonList;
    private int count10;
    private int count20;
    private String code;

    public LessonRes() {
    }
    public LessonRes(List<Lesson> lessonList, int count10, int count20) {
        this.lessonList = lessonList;
        this.count10 = count10;
        this.count20 = count20;
    }


}
