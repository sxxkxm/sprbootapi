package com.template.sprbootapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.template.sprbootapi.data.dto.TbuserDto.*;

public interface TbuserService {
	
	TbuserAfterCreateDto saveTbuser(TbuserCreateDto tbuserCreateDto);
	TbuserSelectDto getTbuser(String id);
	TbuserUpdateDto updateTbuser(TbuserUpdateDto tbuserUpdateDto);
	void deleteTbuser(String id);
	List<TbuserSelectDto> getTbuserList();
	Page<TbuserSelectDto> getPagedTbuserList(Pageable pageable);
	TbuserSelectDto getTbuserByMyBatis(String id);
	
}