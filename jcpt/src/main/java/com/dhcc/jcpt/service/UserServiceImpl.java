package com.dhcc.jcpt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dhcc.jcpt.entity.Permission;
import com.dhcc.jcpt.entity.User;
import com.dhcc.jcpt.mapper.UserMapper;


@Service("userService")
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;

	public User getUserByUserName(String username) {
		return userMapper.getUserByUserName(username);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("用户名：=="+username);
		// 将来连接数据库根据账号查询用户信息
		User user = userMapper.getUserByUserName(username);
		if (user == null) {
			// 如果用户查不到，返回null，由provider来抛出异常
			return null;
		}
		// 根据用户的id查询用户的权限
		List<Permission> permissions = user.getPermList();
		// 将permissions转成数组
		String[] permissionArray = new String[permissions.size()];
		List<String> authorities = new ArrayList<String>();
		for (int i = 0; i < permissions.size(); i++) {
			permissionArray[i] = permissions.get(i).getCode();
			authorities.add(permissions.get(i).getCode());
		}
		// 将userDto转成json
		String principal = JSON.toJSONString(user);
		/*
		 * UserDetails userDetails =
		 * org.springframework.security.core.userdetails.User.withUsername(
		 * principal) .password("123").authorities(permissionArray).build();
		 */
		List<Permission> permissions2 = new ArrayList<Permission>();
		Permission p1 = new Permission();
		p1.setCode("SUPER");
		permissions2.add(p1);
		Permission p2 = new Permission();
		p2.setCode("ADMIN");
		permissions2.add(p2);
		Permission p3 = new Permission();
		p3.setCode("ANONYMOUS");
		//permissions2.add(p3);
		
		System.out.println(user.getFullname()+"==="+user.getPassword());
		// 登录用户名, 从数据库查询出来的用户密码,
		// 用户状态，false表示无效，true表示有效, true, true, true, 获取当前登录用户的角色列表
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), true, true, true, true, getAuthority(permissions2));
		return userDetails;
	}

	// 作用就是返回一个List集合，集合中装入的是角色描述
	public Collection<GrantedAuthority> getAuthority(List<Permission> permissions) {
		//List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
		for (Permission permission : permissions) {
			System.out.println("ROLE_" + permission.getCode());
			//list.add(new SimpleGrantedAuthority("ROLE_" + permission.getCode()));
			auths.add(new SimpleGrantedAuthority("ROLE_" + permission.getCode()));
		}
		return auths;
	}

}
