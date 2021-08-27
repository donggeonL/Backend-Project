package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.userRepository;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "/index";		
	}

}
