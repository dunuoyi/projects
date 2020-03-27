package com.dhcc.order.mapper;

import com.dhcc.order.entity.Department;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentMapper {

    //查询
    @Select("select * from department where name=#{name}")
    Department findByName(@Param("name") String name);
    //添加
    @Insert("insert into department(name,remark,create_date) values(#{name},#{remark},#{createDate})")
    int insert(@Param("name") String name, @Param("remark") String remark, @Param("createDate") Date createDate);

    //-----------------------------通过配置文件xml进行操作数据库------------------------------
    Department getDepaById(int id);

    void insertDepa(Department depa);

    void updateDepa(Department depa);

    void delDepaById(int id);

    List<Department> getDepaPageBystate(Map<String, Object> map);

}
