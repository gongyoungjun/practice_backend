package com.example.test.api.test.controller;

import com.example.test.api.test.dao.Test;
import com.example.test.api.test.dto.TestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private List<Test> users = new ArrayList<>();
    private int id = 0;


    private final MessageSource messageSource;


    // User 추가
    @PostMapping("/user")
    public String add(@RequestBody TestDTO user) {
        users.add(new Test(id, user.getNickname(), user.getPassword()));
        id++;
        return "User 추가 완료";
    }

    // User 조회
    @GetMapping("/user/{id}")
    public Test findById(@PathVariable int id) {
        return users.get(id);
    }

    // User 전체 조회
    @GetMapping("/users")
    public List<Test> findAll() {

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


/**
 * 파일
 * lesson
 * 횟수 별
 * 인원 수
 *//*

    @Operation(summary = "Registered/인원수", description = "Registered/인원수")
    @GetMapping("/read-file/registered/count/{fileName}")
    public ResponseEntity<List<Lesson>> getRegisteredLessons(@PathVariable String fileName) {
        List<Lesson> registeredLessons = empService.registered(fileName);
        return ResponseEntity.ok(registeredLessons);
    }

    */
/**
 * 파일
 * lesson
 * 횟수 별
 * 등록된 사람들
 *//*

    @Operation(summary = "Registered/사람", description = "Registered/사람")
    @GetMapping("/read-file/registered/person/{fileName}")
    public ResponseEntity<Map<String, List<String>>> getRegisteredByCount(@PathVariable String fileName) {
        Map<String, List<String>> registeredMap = empService.getRegisteredByCount(fileName);
        return ResponseEntity.ok(registeredMap);
    }
*/

    /**
     * 파일
     * inputstream
     */
/*    @Operation(summary = "파일 업로드", description = "파일 업로드")
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            FileVo fileVo = empService.uploadFile(file);
            request.setAttribute("fileVo", fileVo);
            return "success-page";
        } catch (IOException e) {
            request.setAttribute("error", "파일 업로드 중 오류가 발생했습니다.");
            return "error-page";
        }
    }*/
    /**
     * 파일
     * tostring
     */
/*    @Override
    public FileVo lessonFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }
        //시퀸스 = 생성해주는 오라클 객체
        //ByteArrayOutputStream =  바이트 배열을 차례대로 읽기
        String fileContent;
        try (InputStream inputStream = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) { // 지정된 byte 시퀀스 생성
            byte[] buffer = new byte[8192]; //바이트 크기 // buffer - 데이터 임시 저장, 전송
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) { // 데이터를 읽기 // -1를 반환하면 데이터 x
                outputStream.write(buffer, 0, bytesRead); // bu 저장 데이터 -> out 출력
            }

            // 파일 내용을 문자열로 변환하여 공백으로 구분
            // 주어진 문자열에서 바이트를 얻습니다.
            fileContent = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
            fileContent = fileContent.replace("\n", " ").replace("\r", " ").replaceAll("\\s+", " ");
        }

        FileVo fileVo = new FileVo();
        fileVo.setFileName(file.getOriginalFilename());
        fileVo.setFilePath(FILE_LESSON_PATH + file.getOriginalFilename());
        fileVo.setFileContent(fileContent);

        return fileVo;
    }*/
    /**
     * 사원목록
     * update
     */
/*    @Operation(summary = "사원 프로필 업데이트", description = "사원 프로필을 업데이트하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> setEmplistUpdate(@Valid @RequestBody EmpDTO empDTO) {

        empService.empListUpdate(empDTO);
        return ResponseEntity.ok("업데이트");
    }*/
    /**
     * 파일
     * losson
     * @PathVariable = 템플릿 변수 처리 {}
     */
/*    @GetMapping("/read-file/{fileName}")
    public ResponseEntity<LessonRes> readFile(@PathVariable String fileName) {
        LessonRes response;
        String code;

        try {
            LessonRes lessonRes = empService.readFile(fileName);
            code = Code.SUCCESS;
            response = new LessonRes(code, lessonRes.getLessonList(), lessonRes.getCount10(), lessonRes.getCount20());
            return ResponseEntity.ok(response);
        } catch (LessonException e) {
            e.printStackTrace();
            code = Code.FAIL;
            response = new LessonRes(code, Collections.emptyList(), 0, 0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }*/
/*    @Operation(summary = "Lesson", description = "Lesson")
    @GetMapping("/read-file/{fileName}")
    public ResponseEntity<LessonRes> readFile(@PathVariable String fileName) {
        try {
            LessonRes lessonRes = empService.readFile(fileName);
            return ResponseEntity.ok(new LessonRes(lessonRes.getLessonList(), lessonRes.getCount10(), lessonRes.getCount20()));
        } catch (LessonException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


}


