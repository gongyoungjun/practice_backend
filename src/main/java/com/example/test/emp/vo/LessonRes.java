package com.example.test.emp.vo;

import lombok.Data;

import java.util.List;

@Data
public class LessonRes {
    private String code;
    private List<Lesson> lessonList;
    private int count10;
    private int count20;


    public LessonRes(String code, List<Lesson> lessonList,int count10, int count20) {
        this.code = code;
        this.lessonList = lessonList;
        this.count10 = count10;
        this.count20 = count20;
    }


}
