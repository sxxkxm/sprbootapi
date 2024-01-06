package com.template.sprbootapi.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.template.sprbootapi.data.dto.RoleTypeDto.RoleTypeCreateDto;
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
@Table(name = "roleType")
@Entity
public class RoleType extends DefaultEntity {
	
	@Column(length = 191, nullable = false, unique = true)
	private String typeName;
	
	@OneToMany(mappedBy = "roleType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();
	
	@Builder
	public RoleType(String id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, 
					String typeName, List<TbuserRoleType> tbuserRoleType) {
		super(id, createdAt, createdBy, updatedAt, updatedBy);
		this.typeName = typeName;
		this.tbuserRoleType = tbuserRoleType;
	}
	
	public RoleTypeCreateDto toCreateDto() {
		return RoleTypeCreateDto.builder()
				  	     	    .typeName(typeName)
				  	     	    .build();
	}
	
}