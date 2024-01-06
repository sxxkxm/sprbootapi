package com.template.sprbootapi.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.sprbootapi.data.entity.RoleType;

public interface RoleTypeRepository extends JpaRepository<RoleType, String>{
	
	RoleType findByTypeName(String typeName);

}
