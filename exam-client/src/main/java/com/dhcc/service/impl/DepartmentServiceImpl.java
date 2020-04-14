package com.dhcc.service.impl;

import com.dhcc.entity.Department;
import com.dhcc.mapper.DepartmentMapper;
import com.dhcc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public int insert(String name, String remark, Date date){
        int result = departmentMapper.insert(name,remark,date);
        return result;
    }

    //-----------------------------通过配置文件xml进行操作数据库------------------------------

    public Department getDepaById(int id){
        return departmentMapper.getDepaById(id);
    }

    public void insertDepa(Department depa){
        departmentMapper.insertDepa(depa);
    }

    public void updateDepa(Department depa){
        departmentMapper.updateDepa(depa);
    }

    public void delDepaById(int id){
        departmentMapper.delDepaById(id);
    }

    public List<Department> getDepaPageBystate(Map<String,Object> map){
        return departmentMapper.getDepaPageBystate(map);
    }


}
