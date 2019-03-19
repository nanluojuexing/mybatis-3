package org.apache.demo.dao;

import org.apache.demo.pojo.StudentEntity;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

    public StudentEntity getStudentById(int id);

    public int addStudent(StudentEntity student);

    public int updateStudentName(@Param("name") String name, @Param("id") int id);

    public StudentEntity getStudentByIdWithClassInfo(int id);
}
