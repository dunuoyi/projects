创建部门表
create table department(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
   remark VARCHAR(40) NOT NULL,
   create_date DATE,
   PRIMARY KEY (id )
);
插入sql语句
INSERT INTO department(NAME,remark,create_date) values('技术部','技术研发',NOW());
INSERT INTO department(NAME,remark,create_date) values('销售部','产品销售',NOW());