package com.spr.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spr.dto.User;
import com.spr.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userServ;
	
	public void setuserServ(UserService userServ)
	{
		this.userServ=userServ;
	}
	
	
	@GetMapping
	public String displayLogin() {
		
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView loginSuccess(@RequestParam("userName") String userName,@RequestParam("password") String password,HttpSession session)
	{
		ModelAndView model=new ModelAndView();
		
		User user1=new User();
		
		user1.setUserName(userName);
		user1.setPassword(password);
	
	try	{
			User user2=userServ.Login(user1);
			
			if(user2!=null)
			{
				if(user2.getUserName()!=null || user2.getPassword() != null)
				{	
					
						if(user2.getRole_id()==11)
						{
							model.setViewName("home");
							session.setAttribute("userName",userName);
							System.out.println("Inside m_role");
							System.out.println(session.getAttribute("userName"));
						}
						else if(user2.getRole_id()==12)
						{
							model.setViewName("S_AdminHome");
							session.setAttribute("userName",userName);
							
							
							
							
						}
						else
						{
							model.setViewName("fir_userHome");
							session.setAttribute("userName",userName);
							System.out.println("Inside normal user");
							System.out.println(session.getAttribute("userName"));
						}
				}
				
			}
		}catch (Exception e) {
			if(userName == null | password == null)
			{
				model.addObject("msg","Invalid User Entry");
				model.setViewName("login");
			}
		}
	
		return model;
		
	}
}