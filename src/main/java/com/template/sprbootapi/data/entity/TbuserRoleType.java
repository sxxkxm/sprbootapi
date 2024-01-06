package com.template.sprbootapi.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.template.sprbootapi.data.entity.common.DefaultEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = UUIDGenerator.class, property = "id")
@Table(name = "tbuser_role_type")
@Entity
public class TbuserRoleType extends DefaultEntity {
	
	@ManyToOne
	@JoinColumn(name = "tbuser_id")
	private Tbuser tbuser;
	
	@ManyToOne
	@JoinColumn(name = "role_type_id")
	private RoleType roleType;
	
}