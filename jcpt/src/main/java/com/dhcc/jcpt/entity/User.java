package com.dhcc.jcpt.entity;

import java.util.List;

import lombok.Data;

@Data
public class User {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Permission> getPermList() {
		return permList;
	}
	public void setPermList(List<Permission> permList) {
		this.permList = permList;
	}
	private String id;
	private String username;
	private String password;
	private String fullname;
	private String mobile;
	
	private List<Permission> permList;

}
