package com.template.sprbootapi.data.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.template.sprbootapi.data.entity.Tbuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TbuserDto {
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbuserCreateDto {
		
		@Schema(description = "username", example="xxx@xxxx.com")
		@NotNull
		@NotEmpty
		@Email
		@Size(max=191)
		private String username;
		
		@Schema(description = "min 8, 1 upper case, 1 number", example="testPass1!")
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")
		@NotNull
		@NotEmpty
		@Size(min=8, max=191)
		private String password;
		
		@Schema(description = "first name", example="Gildong")
		@NotNull
		@NotEmpty
		@Size(max=32)
		private String firstName;
		
		@Schema(description = "last name", example="Hong")
		@NotNull
		@NotEmpty
		@Size(max=32)
		private String lastName;
		
		public Tbuser toEntity() {
			return Tbuser.builder()
					     .username(username)
					     .password(password)
					     .firstName(firstName)
					     .lastName(lastName)
					     .build();
		}
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbuserAfterCreateDto {
		
		@Schema(description = "id", example="")
		private String id;
		
		@Schema(description = "username", example="xxx@xxxx.com")
		@NotNull
		@NotEmpty
		@Email
		@Size(max=191)
		private String username;
		
		@Schema(description = "min 8, 1 upper case, 1 number", example="testPass1!")
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")
		@NotNull
		@NotEmpty
		@Size(min=8, max=191)
		private String password;
		
		@Schema(description = "first name", example="Gildong")
		@NotNull
		@NotEmpty
		@Size(max=32)
		private String firstName;
		
		@Schema(description = "last name", example="Hong")
		@NotNull
		@NotEmpty
		@Size(max=32)
		private String lastName;
		
		public Tbuser toEntity() {
			return Tbuser.builder()
					     .id(id)
					     .username(username)
					     .password(password)
					     .firstName(firstName)
					     .lastName(lastName)
					     .build();
		}
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbuserSelectDto {
		
		@Schema(description = "id", example="")
		private String id;
		
		@Schema(description = "username", example="xxx@xxxx.com")
		private String username;
		
		@Schema(description = "first name", example="Gildong")
		private String firstName;
		
		@Schema(description = "last name", example="Hong")
		private String lastName;
		
		@Schema(description = "roleTypes", example="ROLE_USER")
		private List<String> roleTypes;
		
//		@Schema(description = "tbuserRoleType", example="ROLE_USER")
//		private List<TbuserRoleType> tbuserRoleType;
		
		@Schema(description = "created at", example="2022-01-01 00:00:00.000000")
		private LocalDateTime createdAt;
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbuserUpdateDto {
		
		@NotNull
		@NotEmpty
		@Schema(description = "id", example="")
		private String id;
		
		@Schema(description = "first name", example="Gildong")
		private String firstName;
		
		@Schema(description = "last name", example="Hong")
		private String lastName;
		
		public Tbuser toEntity() {
			return Tbuser.builder()
						 .id(id)
						 .firstName(firstName)
						 .lastName(lastName)
						 .build();
		}
		
	}
	
	@Schema
	@Builder
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TbuserLoginDto {
		
		@Schema(description = "username", example="xxx@xxxx.com")
		@NotNull
		@NotEmpty
		@Email
		@Size(max=191)
		private String username;
		
		@Schema(description = "min 8, 1 upper case, 1 number", example="testPass1!")
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}")
		@NotNull
		@NotEmpty
		@Size(min=8, max=191)
		private String password;
		
		public Tbuser toEntity() {
			return Tbuser.builder()
						 .username(username)
						 .password(password)
						 .build();
		}
		
	}
	
}