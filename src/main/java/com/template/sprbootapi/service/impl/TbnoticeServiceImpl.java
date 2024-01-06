package com.template.sprbootapi.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeCreateDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeSelectDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeUpdateDto;
import com.template.sprbootapi.data.entity.Tbnotice;
import com.template.sprbootapi.data.mapper.TbnoticeMapper;
import com.template.sprbootapi.data.repository.TbnoticeRepository;
import com.template.sprbootapi.exception.NoMatchingUserException;
import com.template.sprbootapi.service.TbnoticeService;

import jakarta.transaction.Transactional;

@Service
public class TbnoticeServiceImpl implements TbnoticeService {

	private final TbnoticeRepository tbnoticeRepository;
	private final TbnoticeMapper tbnoticeMapper;
	
	public TbnoticeServiceImpl(TbnoticeRepository tbnoticeRepository, TbnoticeMapper tbnoticeMapper) {
		this.tbnoticeRepository = tbnoticeRepository;
		this.tbnoticeMapper = tbnoticeMapper;
	}
	
	@Override
	public TbnoticeSelectDto saveTbnotice(TbnoticeCreateDto tbnoticeCreateDto) {
		Tbnotice tbnotice = tbnoticeCreateDto.toEntity();
		tbnotice = tbnoticeRepository.save(tbnotice);
		return tbnotice.toSelectDto();
	}

	@Override
	public TbnoticeSelectDto getTbnotice(String id) {
		Tbnotice tbnotice = tbnoticeRepository.findById(id).orElseThrow(new Supplier<NoMatchingUserException>() {
			@Override
			public NoMatchingUserException get() {
				return new NoMatchingUserException("id : " + id);
			}
		});
		return tbnotice.toSelectDto();
	}
	
	@Transactional
	@Override
	public TbnoticeSelectDto updateTbnotice(TbnoticeUpdateDto tbnoticeUpdateDto) {
		Tbnotice tbnotice = tbnoticeRepository.findById(tbnoticeUpdateDto.getId()).orElseThrow(new Supplier<NoMatchingUserException>() {
			@Override
			public NoMatchingUserException get() {
				return new NoMatchingUserException("id : " + tbnoticeUpdateDto.getId());
			}
		});
		if(tbnoticeUpdateDto.getTitle() != null) {
			tbnotice.setTitle(tbnoticeUpdateDto.getTitle());
		}
		if(tbnoticeUpdateDto.getContent() != null) {
			tbnotice.setContent(tbnoticeUpdateDto.getContent());
		}
		return tbnotice.toSelectDto();
	}
	
	@Override
	public void deleteTbnotice(String id) {
		try {
			tbnoticeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			throw new NoMatchingUserException("id : " + id);
		}
	}
	
	@Override
	public List<TbnoticeSelectDto> getTbnoticeList(){
		List<Tbnotice> tbnoticeList = tbnoticeRepository.findAll();
		return tbnoticeList.stream().map(tbnotice -> tbnotice.toSelectDto()).collect(Collectors.toList());
	}
	
	@Override
	public Page<TbnoticeSelectDto> getPagedTbnoticeList(Pageable pageable){
		Page<Tbnotice> tbnoticePage = tbnoticeRepository.findAll(pageable);
		Page<TbnoticeSelectDto> tbnoticeDtoPage = tbnoticePage.map(new Function<Tbnotice, TbnoticeSelectDto>(){
			@Override
			public TbnoticeSelectDto apply(Tbnotice tbnotice) {
				return tbnotice.toSelectDto();
			}
		});
		return tbnoticeDtoPage;
	}

}