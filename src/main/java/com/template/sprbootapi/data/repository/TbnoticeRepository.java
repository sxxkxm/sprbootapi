package com.template.sprbootapi.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.sprbootapi.data.entity.Tbnotice;

public interface TbnoticeRepository extends JpaRepository<Tbnotice, String> {}