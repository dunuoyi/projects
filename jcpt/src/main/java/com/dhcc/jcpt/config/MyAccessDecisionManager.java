package com.dhcc.jcpt.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * @auther Mr.Liao
 * @date 2019/8/20 21:18
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		// 用户用哪些角色
		/*
		 * System.out.println(authentication.getClass()); Collection<? extends
		 * GrantedAuthority> authorities = authentication.getAuthorities();
		 * authorities.iterator(); //需要角色用这个资源权限 Iterator<ConfigAttribute>
		 * iterator = configAttributes.iterator(); while (iterator.hasNext()){
		 * ConfigAttribute configAttribute = iterator.next(); //访问该请求url需要的角色信息
		 * String needRole = configAttribute.getAttribute();
		 * //如果只是登陆就能访问，即没有匹配到资源信息 if ("ROLE_login".equals(needRole)){
		 * //判断是否登陆，没有登陆则authentication是AnonymousAuthenticationToken接口实现类的对象 if
		 * (authentication instanceof AnonymousAuthenticationToken){ throw new
		 * BadCredentialsException("未登录"); } else return; }
		 * 
		 * //如果匹配上了资源信息，就拿登陆用户的权限信息来对比是否存在于已匹配的角色集合中 for (GrantedAuthority
		 * authority : authorities) { if
		 * (authority.getAuthority().equals(needRole)){ return; } } }
		 * System.out.println(
		 * "===================================================00"); return;
		 * //如果没有匹配上，则权限不足 //throw new AccessDeniedException("权限不足");
		 */
		System.out.println("==============进行投票============"); 
		if (configAttributes == null) {
			return;
		}
		System.out.println(object.toString()); // object is a URL.
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {//对所需的权限进行遍历，只要拥有一个，就可以访问
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			System.out.println("needRole=="+needRole);
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				System.out.println(needRole+"==="+ga.getAuthority());
				if (needRole.equals(ga.getAuthority())) { // ga is user's role.
					return;
				}
			}
		}
		System.out.println("==============no right============"); 
		throw new AccessDeniedException("权限不足");

	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
