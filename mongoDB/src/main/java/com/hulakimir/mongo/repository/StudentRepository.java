package com.hulakimir.mongo.repository;

import com.hulakimir.mongo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author xiangwei
 * @date 2020-09-03 2:42 下午
 */
public interface StudentRepository extends MongoRepository<Student, String>
{
    @Query("{'stu_name':?0}}")
    List<Student> findStudentsByStu_name(String stu_name);

    @Query("{'stu_name':{$regex:'?0'}}")
    List<Student> findStudentsByStu_nameBetween(String stu_name);

    @Query("{'stu_name':{$regex:'^?0'}}")
    List<Student> findStudentsByStu_nameStartingWith(String stu_name);

    @Query("{$and:[{'stu_name':?0},{'age':?1}]}")
    List<Student> findStudentsByStu_nameaAndAge(String stu_name,Integer age);
}