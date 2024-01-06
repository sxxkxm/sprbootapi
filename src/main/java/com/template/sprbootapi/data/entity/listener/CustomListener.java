package com.template.sprbootapi.data.entity.listener;

import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomListener {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);
	
	@PostLoad
	public void postLoad(Entity entity) {
		LOGGER.info("postLoad");
	}
	
	@PrePersist
	public void prePersist(Entity entity) {
		LOGGER.info("prePersist");
	}
	
	@PostPersist
	public void postPersist(Entity entity) {
		LOGGER.info("postPersist");
	}
	
	@PreUpdate
	public void preUpdate(Entity entity) {
		LOGGER.info("preUpdate");
	}
	
	@PostUpdate
	public void postUpdate(Entity entity) {
		LOGGER.info("postUpdate");
	}
	
	@PreRemove
	public void preRemove(Entity entity) {
		LOGGER.info("preRemove");
	}
	
	@PostRemove
	public void postRemove(Entity entity) {
		LOGGER.info("postRemove");
	}

}