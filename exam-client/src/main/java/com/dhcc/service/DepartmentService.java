package com.dhcc.service;

import com.dhcc.entity.Department;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DepartmentService {


    public int insert(String name, String remark, Date date);

    //-----------------------------通过配置文件xml进行操作数据库------------------------------

    public Department getDepaById(int id);

    public void insertDepa(Department depa);

    public void updateDepa(Department depa);

    public void delDepaById(int id);

    public List<Department> getDepaPageBystate(Map<String,Object> map);


}
