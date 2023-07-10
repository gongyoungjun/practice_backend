package com.example.test.emp.controller;

import com.example.test.emp.dto.testDTO;
import com.example.test.emp.dao.test;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class testController {

    private List<test> users = new ArrayList<>();
    private int id = 0;


    private final MessageSource messageSource;


    // User 추가
    @PostMapping("/user")
    public String add(@RequestBody testDTO user) {
        users.add(new test(id, user.getNickname(), user.getPassword()));
        id++;
        return "User 추가 완료";
    }

    // User 조회
    @GetMapping("/user/{id}")
    public test findById(@PathVariable int id) {
        return users.get(id);
    }

    // User 전체 조회
    @GetMapping("/users")
    public List<test> findAll() {

        return users;
    }

    @GetMapping("/testcall")
    public ArrayList<String> test(Locale locale) {
        ArrayList<String> msgs = new ArrayList<>();

        msgs.add(messageSource.getMessage("test", null, locale));
        msgs.add(messageSource.getMessage("save", null, locale));

        msgs.add(messageSource.getMessage("test", null, locale.ENGLISH));
        msgs.add(messageSource.getMessage("save", null, locale.ENGLISH));

        msgs.add(messageSource.getMessage("test", null, locale.KOREAN));


        return msgs;
    }


    /**
     * FileOutputStream 클래스
     * 바이트 기반 출력 스트림 최상위 클래스인 OutputStream 를 상속
     * FileOutputStream 은 OutputStream 을 상속한 클래스로 데이터를 바이트 배열(byte[])로 입력
     *
     * @param fileData
     * @param filePath
     * @param fileName
     */
    public static void fileOutputStreamToFile(String fileData, String filePath, String fileName) {
        String d = fileData;
        //isMkdirs(filePath);

        File file = new File(filePath + fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);

            // FileOutputStream 클래스가 파일에 바이트로 내보내기 때문에 출력할 데이터를 바이트 배열(byte[])로 변환
            byte[] b = d.getBytes();
            fos.write(b);
            fos.flush();

            System.out.println("fileOutputStreamToFile File Created");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * FileInputStream 클래스
     * 바이트 기반 출력 스트림 최상위 클래스인 InputStream 를 상속
     * FileInputStream 은 InputStream 을 상속한 클래스로 데이터를 바이트 배열(byte[])로 출력
     *
     * @param fileMap
     * @param filePath
     * @param fileName
     */
    public static void fileInputStream(Map<String, Object> fileMap, String filePath, String fileName) {
        String fileFullPath = filePath + fileName;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(fileFullPath);

            int size = fis.available();//스트림으로부터 현재 읽을 수 있는 바이트 수를 얻음
            byte[] buf = new byte[size];

            //최대 len개의 byte를 읽어서, byte[] 배열 b의 지정된 위치(off)부터 저장하고 읽은 바이트 수 반환
            fis.read(buf, 0, size);
            System.out.println("FileInputStream Read");
            fileMap.put("retData", new String(buf));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}


