package com.example.test.emp.service;

import com.example.test.emp.dto.EmpDTO;
import com.example.test.emp.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TestService {


/*    *//**
     * 파일
     * 횟수 별
     * 카운팅
     *//*
    List<Lesson> registered(String fileName);

    *//**
     * 파일
     * 횟수 별
     * 등록된 사람들
     *//*
    Map<String, List<String>> getRegisteredByCount(String fileName);
    *//**
     * 엑셀
     * 업로드
     *//*
    void readExcelFile();*/
}
