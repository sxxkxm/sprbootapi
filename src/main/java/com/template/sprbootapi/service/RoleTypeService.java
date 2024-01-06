package com.template.sprbootapi.service;

import com.template.sprbootapi.data.dto.RoleTypeDto.RoleTypeCreateDto;

public interface RoleTypeService {
	
	RoleTypeCreateDto saveRoleType(RoleTypeCreateDto roleTypeCreateDto);
	
}