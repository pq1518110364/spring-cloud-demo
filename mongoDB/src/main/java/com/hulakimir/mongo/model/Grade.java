package com.hulakimir.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author xiangwei
 * @date 2020-09-03 2:40 下午
 */
@Document(collection = "grade")//对应表名
@Data//setter getter toString
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
public class Grade
{
    @Id//主键
    private String id;
    private Integer grade_id;
    private String grade_name;
    private List<Student> student_list;
}

