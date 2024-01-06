package com.template.sprbootapi.service.impl;

import org.springframework.stereotype.Service;

import com.template.sprbootapi.data.dto.RoleTypeDto.RoleTypeCreateDto;
import com.template.sprbootapi.data.entity.RoleType;
import com.template.sprbootapi.data.repository.RoleTypeRepository;
import com.template.sprbootapi.service.RoleTypeService;

@Service
public class RoleTypeServiceImpl implements RoleTypeService {

	private final RoleTypeRepository roleTypeRepository;
	
	public RoleTypeServiceImpl(RoleTypeRepository roleTypeRepository) {
		this.roleTypeRepository = roleTypeRepository;
	}

	@Override
	public RoleTypeCreateDto saveRoleType(RoleTypeCreateDto roleTypeCreateDto) {
		RoleType roleType = roleTypeCreateDto.toEntity();
		return roleTypeRepository.save(roleType).toCreateDto();
	}
	
}