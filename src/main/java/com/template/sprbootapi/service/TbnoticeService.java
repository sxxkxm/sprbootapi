package com.template.sprbootapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeCreateDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeSelectDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeUpdateDto;

public interface TbnoticeService {
	
	TbnoticeSelectDto saveTbnotice(TbnoticeCreateDto tbnoticeCreateDto);
	TbnoticeSelectDto getTbnotice(String id);
	TbnoticeSelectDto updateTbnotice(TbnoticeUpdateDto tbnoticeUpdateDto);
	void deleteTbnotice(String id);
	List<TbnoticeSelectDto> getTbnoticeList();
	Page<TbnoticeSelectDto> getPagedTbnoticeList(Pageable pageable);
	
}