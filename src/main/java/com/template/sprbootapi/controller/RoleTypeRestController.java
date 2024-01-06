package com.template.sprbootapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.sprbootapi.data.dto.RoleTypeDto.RoleTypeCreateDto;
import com.template.sprbootapi.service.RoleTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roletype")
public class RoleTypeRestController {
	
	private final RoleTypeService roleTypeService;
	
	public RoleTypeRestController(RoleTypeService roleTypeService) {
		this.roleTypeService = roleTypeService;
	}
	
	@PostMapping("")
	public ResponseEntity<RoleTypeCreateDto> saveRoleType(@Valid @RequestBody RoleTypeCreateDto roleTypeCreateDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(roleTypeService.saveRoleType(roleTypeCreateDto));
	}
	
}