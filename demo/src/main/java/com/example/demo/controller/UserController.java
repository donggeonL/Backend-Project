package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.userRepository;

@Controller
public class UserController {
	@Autowired
	userRepository ur;
	
	@GetMapping
	public String usermodel(Model model) {
		model.addAttribute(ur);
		ur.save(null);
		
		return "/home";
	}
	@GetMapping("/signup")
	public String signup() {
		
		return "signup";
	}
}
