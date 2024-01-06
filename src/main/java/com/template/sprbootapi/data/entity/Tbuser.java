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
import com.template.sprbootapi.data.dto.TbuserDto.*;
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
@Table(name = "tbuser")
@Entity
public class Tbuser extends DefaultEntity {
	
	@Column(length = 191, nullable = false, unique = true)
	private String username;
	
	@Column(length = 191, nullable = false)
	private String password;
	
	@Column(length = 32, nullable = false)
	private String firstName;
	
	@Column(length = 32, nullable = false)
	private String lastName;
	
	@OneToMany(mappedBy = "tbuser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();
    
	@Builder
	public Tbuser(String id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, 
				   String username, String password, String firstName, String lastName, List<TbuserRoleType> tbuserRoleType) {
		super(id, createdAt, createdBy, updatedAt, updatedBy);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.tbuserRoleType = tbuserRoleType;
	}
	
	public TbuserCreateDto toCreateDto() {
		return TbuserCreateDto.builder()
				  	     	  .username(username)
				  	     	  .password(password)
				  	     	  .firstName(firstName)
				  	     	  .lastName(lastName)
				  	     	  .build();
	}
	public TbuserAfterCreateDto toAfterCreateDto() {
		return TbuserAfterCreateDto.builder()
								   .id(super.getId())
								   .username(username)
								   .password(password)
								   .firstName(firstName)
								   .lastName(lastName)
								   .build();
	}
	
	public TbuserSelectDto toSelectDto() {
		
		List<String> roleTypes = new ArrayList<>();
		
		for(TbuserRoleType it : this.tbuserRoleType) {
			String roleTypeName = it.getRoleType().getTypeName();
			roleTypes.add(roleTypeName);
		}
		
		return TbuserSelectDto.builder()
							  .id(super.getId())
							  .username(username)
							  .firstName(firstName)
							  .lastName(lastName)
							  .roleTypes(roleTypes)
							  .createdAt(super.getCreatedAt())
							  .build();
	}
	
	public TbuserUpdateDto toUpdateDto() {
		return TbuserUpdateDto.builder()
							  .id(super.getId())
							  .firstName(firstName)
							  .lastName(lastName)
							  .build();
	}
	
    public List<TbuserRoleType> getRoleList(){
        if(this.tbuserRoleType.size() > 0){
        	return tbuserRoleType;
        }
        return new ArrayList<>();
    }
	
}