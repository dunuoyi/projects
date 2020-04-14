package com.dhcc.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Department {

    private int id;
    private String name;
    private String remark;
    private Date createDate;
    private String state;

    public String toString(){
        return "Department{id="+id+",name="+name+",remark="+remark+"}";
    }


}
