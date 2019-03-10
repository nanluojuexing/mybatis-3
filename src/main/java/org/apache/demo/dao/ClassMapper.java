package org.apache.demo.dao;

import org.apache.ibatis.annotations.Param;

public class ClassMapper {

    public int updateClassName(@Param("name") String className, @Param("id") int id);
}
