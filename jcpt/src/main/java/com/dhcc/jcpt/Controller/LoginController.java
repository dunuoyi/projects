package com.dhcc.jcpt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dhcc.jcpt.entity.User;
import com.dhcc.jcpt.service.UserServiceImpl;

@Controller
public class LoginController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@RequestMapping("/loginUi")
	public ModelAndView loginUi(){
		
		
		User user = userServiceImpl.getUserByUserName("DuJianLing");
		System.out.println("user=="+user.getFullname());
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg","请登录");
		mv.setViewName("login");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/r1")
	public String login(){
		
		return "444444444444444";
	}
	
	@ResponseBody
	@RequestMapping("/r2")
	public String r2(){
		
		return "5555555555555555555";
	}
	
	
	
	
	

}
