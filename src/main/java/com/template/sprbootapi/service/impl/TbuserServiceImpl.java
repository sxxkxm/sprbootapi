package com.template.sprbootapi.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.template.sprbootapi.data.constants.RoleTypes;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserAfterCreateDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserCreateDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserSelectDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserUpdateDto;
import com.template.sprbootapi.data.entity.RoleType;
import com.template.sprbootapi.data.entity.Tbuser;
import com.template.sprbootapi.data.entity.TbuserRoleType;
import com.template.sprbootapi.data.mapper.TbuserMapper;
import com.template.sprbootapi.data.repository.RoleTypeRepository;
import com.template.sprbootapi.data.repository.TbuserRepository;
import com.template.sprbootapi.data.repository.TbuserRoleTypeRepository;
import com.template.sprbootapi.exception.NoMatchingUserException;
import com.template.sprbootapi.service.TbuserService;

import jakarta.transaction.Transactional;

@Service
public class TbuserServiceImpl implements TbuserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TbuserRepository tbuserRepository;
	private final RoleTypeRepository roleTypeRepository;
	private final TbuserRoleTypeRepository tbuserRoleTypeRepository;
	private final TbuserMapper tbuserMapper;
	
	public TbuserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, TbuserRepository tbuserRepository, RoleTypeRepository roleTypeRepository, TbuserRoleTypeRepository tbuserRoleTypeRepository, TbuserMapper tbuserMapper) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.tbuserRepository = tbuserRepository;
		this.roleTypeRepository = roleTypeRepository;
		this.tbuserRoleTypeRepository = tbuserRoleTypeRepository;
		this.tbuserMapper = tbuserMapper;
	}
	
	@Override
	public TbuserAfterCreateDto saveTbuser(TbuserCreateDto tbuserCreateDto) {
		Tbuser tbuser = tbuserCreateDto.toEntity();
		String rawPassword = tbuser.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		tbuser.setPassword(encPassword);
		tbuser = tbuserRepository.save(tbuser);
		
		RoleType roleType = roleTypeRepository.findByTypeName(RoleTypes.ROLE_USER.getStringValue());
		TbuserRoleType tbuserRoleType = TbuserRoleType.builder().tbuser(tbuser).roleType(roleType).build();
//<<<<<<< HEAD
		tbuserRoleTypeRepository.save(tbuserRoleType);	
		
		return tbuser.toAfterCreateDto();
//=======
//		tbuserRoleTypeRepository.save(tbuserRoleType);
//		return tbuser.toCreateDto();
//>>>>>>> 08b6103 (note)
	}

	@Override
	public TbuserSelectDto getTbuser(String id) {
		Tbuser tbuser = tbuserRepository.findById(id).orElseThrow(new Supplier<NoMatchingUserException>() {
			@Override
			public NoMatchingUserException get() {
				return new NoMatchingUserException("id : " + id);
			}
		});
		return tbuser.toSelectDto();
	}
	
	@Transactional
	@Override
	public TbuserUpdateDto updateTbuser(TbuserUpdateDto tbuserUpdateDto) {
		Tbuser tbuser = tbuserRepository.findById(tbuserUpdateDto.getId()).orElseThrow(new Supplier<NoMatchingUserException>() {
			@Override
			public NoMatchingUserException get() {
				return new NoMatchingUserException("id : " + tbuserUpdateDto.getId());
			}
		});
		if(tbuserUpdateDto.getFirstName() != null) {
			tbuser.setFirstName(tbuserUpdateDto.getFirstName());
		}
		if(tbuserUpdateDto.getLastName() != null) {
			tbuser.setLastName(tbuserUpdateDto.getLastName());
		}
		return tbuser.toUpdateDto();
	}
	
	@Override
	public void deleteTbuser(String id) {
		try {
			tbuserRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			throw new NoMatchingUserException("id : " + id);
		}
	}
	
	@Override
	public List<TbuserSelectDto> getTbuserList(){
		List<Tbuser> tbuserList = tbuserRepository.findAll();
		return tbuserList.stream().map(tbuser -> tbuser.toSelectDto()).collect(Collectors.toList());
	}
	
	@Override
	public Page<TbuserSelectDto> getPagedTbuserList(Pageable pageable){
		Page<Tbuser> tbuserPage = tbuserRepository.findAll(pageable);
		Page<TbuserSelectDto> tbuserDtoPage = tbuserPage.map(new Function<Tbuser, TbuserSelectDto>(){
			@Override
			public TbuserSelectDto apply(Tbuser tbuser) {
				return tbuser.toSelectDto();
			}
		});
		return tbuserDtoPage;
	}

	@Override
	public TbuserSelectDto getTbuserByMyBatis(String id) {
		return tbuserMapper.findById(id).toSelectDto();
	}
	
}