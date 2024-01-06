package com.template.sprbootapi.controller.page.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Controller
@RequestMapping("/admin/tbnotice/")
public class TbnoticeAdminController {
	
	@GetMapping("/insert")
	public String getTbnoticeAdminInsert() {
		return "admin/tbnotice/insert";   
	}
	
	@GetMapping("/update/{tbnoticeId}")
	public String getTbnoticeAdminUpdate(@PathVariable String tbnoticeId, Model model) {
		model.addAttribute("tbnoticeId", tbnoticeId);
		return "admin/tbnotice/update";
	}
	
	@GetMapping("/detail/{tbnoticeId}")
	public String getTbnoticeAdminDetail(@PathVariable String tbnoticeId, Model model) {
		model.addAttribute("tbnoticeId", tbnoticeId);
		return "admin/tbnotice/detail";
	}
	
	@GetMapping("/list")
	public String getTbnoticeAdminList() {
		return "admin/tbnotice/list";   
	}
	
}