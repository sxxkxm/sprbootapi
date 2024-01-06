package com.template.sprbootapi.data.entity.common;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class DefaultEntity {
	
	@Id
	@Column(columnDefinition = "CHAR(36)")
	private String id;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@CreatedBy
	@Column(updatable = false)
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
    /**
	 *  Persist 되기 이전에 Id 생성 함수
	 */
	@PrePersist
	public void onPrePersist() {
	    this.id = UUID.randomUUID().toString().replace("-", "");
	}
	
}