package com.lhy.clock_in.controller;

import com.lhy.clock_in.entity.Student;
import com.lhy.clock_in.service.StudentService;
import com.lhy.clock_in.util.ExcelToMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传Excel数据到MongoDB
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Excel表格插入数据到MongoDB
     * @param excelFile
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/excleimport", method = RequestMethod.POST)
    public Map<String, Object> excleimport(@RequestParam MultipartFile excelFile,
                                           HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> map = new HashMap<String, Object>();
        String name = excelFile.getOriginalFilename();
        if (!name.endsWith(".xls") && !name.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
            map.put("result", "文件类型错误");
        } else {
            map.put("result", ExcelToMongo.getDataFromExcel(excelFile.getInputStream()));
        }
        return map;
    }

    /**
     * 查询已经签到的同学
     * @return
     */
    @GetMapping("/clockStudent")
    public List<Student> clockStudent(){
        return studentService.clockStudent();
    }

    /**
     * 查询未签到的同学
     * @return
     */
    @GetMapping("/noClockStudent")
    public List<Student> noClockStudent(){
        return studentService.noClockStudent();
    }

    /**
     * 学生签到
     * @param name
     * @return
     */
    @PutMapping("/student")
    public String updateStudent(String name){
        return studentService.updateStudent(name);
    }
}
