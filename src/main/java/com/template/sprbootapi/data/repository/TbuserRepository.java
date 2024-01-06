package com.template.sprbootapi.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.template.sprbootapi.data.entity.Tbuser;

public interface TbuserRepository extends JpaRepository<Tbuser, String> {
	
	Tbuser findByUsername(String email);
	
	@EntityGraph(attributePaths = {"tbuserRoleType.roleType"})
	Optional<Tbuser> findEntityGraphRoleTypeById(String id);
	
}