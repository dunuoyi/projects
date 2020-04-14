package com.dhcc.controller;


import com.dhcc.entity.Department;
import com.dhcc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value="/insertdepartment", method= RequestMethod.GET)
    public Integer insertDepartment(String name, String remark, Date date){
        return departmentService.insert(name, remark, date);
    }


    //-----------------------------通过配置文件xml进行操作数据库------------------------------
    @RequestMapping("/getDepaById")
    public String getDepaById(int id){//根据id查询对象
        return departmentService.getDepaById(id).toString();
    }

    @RequestMapping("/insertDepa")
    public String insertDepa(Department department){//保存对象
        department.setName("aaaaaaaaaa");
        department.setRemark("000000000000");
        department.setCreateDate(new Date());
        department.setState("0");
        departmentService.insertDepa(department);
        return "保存成功";
    }

    @RequestMapping("/updateDepa")
    public String updateDepa(Department department){//更新对象
        department.setId(6);
        department.setName("更新后的内容");
        department.setRemark("000000000000");
        department.setCreateDate(new Date());
        department.setState("0");
        departmentService.updateDepa(department);
        return "更新成功";
    }

    @RequestMapping("/delDepaById")
    public String delDepaById(int id){//根据id删除对象
        departmentService.delDepaById(id);
        return "删除成功";
    }

    @RequestMapping("/getDepaPageBystate")
    public List<Department> getDepaPageBystate(int beginIndex, int pageSize){//查询对象，实现分页
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderDir","desc");
        map.put("orderBy","create_date");
        map.put("state","1");
        map.put("beginIndex",beginIndex);
        map.put("pageSize",pageSize);
        return departmentService.getDepaPageBystate(map);
    }


}
