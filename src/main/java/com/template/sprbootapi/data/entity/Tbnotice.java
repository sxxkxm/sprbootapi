package com.template.sprbootapi.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeCreateDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeSelectDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeUpdateDto;
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
@Table(name = "tbnotice")
@Entity
public class Tbnotice extends DefaultEntity {
	
	@Column(length = 191, nullable = false)
	private String title;
	
	@Column(length = 2000, nullable = false)
	private String content;
	
	@Column(length = 2000, nullable = true)
	private String content2;
	
	@Builder
	public Tbnotice(String id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, 
				    String title, String content) {
		super(id, createdAt, createdBy, updatedAt, updatedBy);
		this.title = title;
		this.content = content;
	}
	
	public TbnoticeCreateDto toCreateDto() {
		return TbnoticeCreateDto.builder()
				  	     	    .title(title)
				  	     	    .content(content)
				  	     	    .build();
	}
	
	public TbnoticeSelectDto toSelectDto() {
		return TbnoticeSelectDto.builder()
							    .id(super.getId())
							    .title(title)
							    .content(content)
							    .createdAt(super.getCreatedAt())
							    .build();
	}
	
	public TbnoticeUpdateDto toUpdateDto() {
		return TbnoticeUpdateDto.builder()
							    .id(super.getId())
							    .title(title)
							    .content(content)
							    .build();
	}
	
}