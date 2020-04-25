package com.lhy.clock_in.service;

import com.lhy.clock_in.entity.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void find(){
        List<Student> studentList = studentService.clockStudent();
        for (Student student : studentList){
            System.out.println(student);
        }
    }

    @Test
    void updateStudent(){
        System.out.println(studentService.updateStudent("拉拉拉"));
    }
}
