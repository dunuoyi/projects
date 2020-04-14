package com.dhcc.jcpt.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 
 * @author Robin
 * 
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	//private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MySecurityMetadataSource() {
		loadResourceDefine();
	}
	//定义所有权限的配置（程序启动的时候加载进来）
	private void loadResourceDefine() {
		System.out.println("=========进行权限加载=======");
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ROLE_ADMIN");
		atts.add(ca);
		resourceMap.put("/index.jsp", atts);
		resourceMap.put("/i.jsp", atts);
		
		Collection<ConfigAttribute> atts3 = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca2 = new SecurityConfig("ROLE_SUPER");
		ConfigAttribute ca3 = new SecurityConfig("ROLE_USER");
		atts3.add(ca3);
		atts3.add(ca2);
		resourceMap.put("/r1", atts3);
		
		

	}

	// According to a URL, Find out permission configuration of this URL.
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//1、可以直接定义一个map，存放路径和code的对应，在投票时候在直接去进行判断
		
		//2、直接查询数据库（效率估计不太好）
		
		//3、 guess object is a URL.
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		System.out.println("===================================="+url);
		while (ite.hasNext()) {
			String resURL = ite.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL + "**");
			if (requestMatcher.matches(((FilterInvocation) object).getHttpRequest())) {
				System.out.println("resURL=="+resURL);
				System.out.println(resourceMap.get(resURL));
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}
