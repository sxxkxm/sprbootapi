package com.template.sprbootapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.template.sprbootapi.data.entity.RoleType;
import com.template.sprbootapi.data.repository.RoleTypeRepository;


@SpringBootApplication
public class SprbootapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprbootapiApplication.class, args);
	}

    @Bean
    CommandLineRunner run(RoleTypeRepository roleTypeRepository) throws Exception {
		return (String[] args) -> {
//			RoleType roleTypeUser = RoleType.builder().typeName("ROLE_USER").build();
//			RoleType roleTypeManager = RoleType.builder().typeName("ROLE_MANAGER").build();
//			RoleType roleTypeAdmin = RoleType.builder().typeName("ROLE_ADMIN").build();
//			
//			roleTypeRepository.save(roleTypeUser);
//			roleTypeRepository.save(roleTypeManager);
//			roleTypeRepository.save(roleTypeAdmin);
		};
	}

}
