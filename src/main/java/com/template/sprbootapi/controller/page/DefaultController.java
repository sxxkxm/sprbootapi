package com.template.sprbootapi.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;

// import springfox.documentation.annotations.ApiIgnore;

//@ApiIgnore
@Hidden
@Controller
public class DefaultController {
	
	@GetMapping("/doc")
	public String getSwagger() {
		//return "redirect:/swagger-ui.html";  
		return "redirect:/swagger-ui/index.html";
	}
	@GetMapping({"", "/", "/index"})
	public String getIndex() {
		return "frontend/index";   
//		return "index";   
	}
	
	@GetMapping({"/login"})
	public String login() {
		return "login";   
	}
	@GetMapping({"/signup"})
	public String signup() {
		return "signup";   
	}
	@GetMapping({"/test"})
	public String test() {
		return "test";   
	}
	
}