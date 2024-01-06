package com.template.sprbootapi.controller.page.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Controller
@RequestMapping("/admin/tbuser/")
public class TbuserAdminController {
	
	@GetMapping("/login")
	public String getAdminLogin() {
		return "admin/tbuser/login";   
	}
	
}