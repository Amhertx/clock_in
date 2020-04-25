package com.lhy.clock_in.service;

import com.lhy.clock_in.entity.Student;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 查询未签到的学生
     * @return
     */
    public List<Student> noClockStudent() {
        Criteria criteria = new Criteria();
        criteria.and("status").is("0.0");
        Query query = new Query(criteria);
        List<Student> studentList = mongoTemplate.find(query, Student.class);
        return studentList;
    }

    /**
     * 查询已签到的学生
     * @return
     */
    public List<Student> clockStudent() {
        Criteria criteria = new Criteria();
        criteria.and("status").is("1.1");
        Query query = new Query(criteria);
        List<Student> studentList = mongoTemplate.find(query, Student.class);
        return studentList;
    }

    /**
     * 签到
     * @param name
     * @return ApiResponse
     */
    public String updateStudent(String name) {
        try {
            Query query = new Query(Criteria.where("nama").is(name));
            Update update = new Update();
            update.set("status", "1.1");

            UpdateResult result = mongoTemplate.upsert(query, update, Student.class);
            long count = result.getModifiedCount();
            if (count > 0) {
                return "更新成功";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "更新失败";
    }

}
