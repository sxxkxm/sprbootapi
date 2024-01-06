package com.template.sprbootapi.data.dto;

import java.time.LocalDateTime;

import com.template.sprbootapi.data.entity.Tbnotice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TbnoticeDto {
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbnoticeCreateDto {
		
		@Schema(description = "notice title", required=true, example="title")
		@NotNull
		@NotEmpty
		@Size(max=191)
		private String title;
		
		@Schema(description = "notice content", required=true, example="content")
		@NotNull
		@NotEmpty
		@Size(max=2000)
		private String content;
		
		public Tbnotice toEntity() {
			return Tbnotice.builder()
						   .title(title)
						   .content(content)
						   .build();
		}
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbnoticeSelectDto {
		
		@Schema(description = "id", example="")
		private String id;
		
		@Schema(description = "notice title", required=true, example="title")
		private String title;
		
		@Schema(description = "notice content", required=true, example="content")
		private String content;
		
		@Schema(description = "created at", example="2022-01-01 00:00:00.000000")
		private LocalDateTime createdAt;
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbnoticeUpdateDto {
		
		@Schema(description = "id", required=true, example="")
		private String id;
		
		@Schema(description = "notice title", required=true, example="title")
		private String title;
		
		@Schema(description = "notice content", required=true, example="content")
		private String content;
		
		public Tbnotice toEntity() {
			return Tbnotice.builder()
						   .id(id)
						   .title(title)
						   .content(content)
					       .build();
		}
		
	}
	
}