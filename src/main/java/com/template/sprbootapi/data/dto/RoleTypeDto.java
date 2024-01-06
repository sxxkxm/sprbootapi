package com.template.sprbootapi.data.dto;

import com.template.sprbootapi.data.entity.RoleType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RoleTypeDto {
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RoleTypeCreateDto {
		
		@Schema(description = "typeName", example = "ROLE_XXX")
		@NotNull
		@NotEmpty
		@Size(max=191)
		private String typeName;
		
		public RoleType toEntity() {
			return RoleType.builder()
							   	 .typeName(typeName)
							   	 .build();
		}
		
	}
	
}