package com.template.sprbootapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeCreateDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeSelectDto;
import com.template.sprbootapi.data.dto.TbnoticeDto.TbnoticeUpdateDto;
import com.template.sprbootapi.service.TbnoticeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tbnotice")
public class TbnoticeRestController {
	
	private final TbnoticeService tbnoticeService;
	
	public TbnoticeRestController(TbnoticeService tbnoticeService) {
		this.tbnoticeService = tbnoticeService;
	}

	@PreAuthorize("permitAll()")
	@PostMapping("")
	public ResponseEntity<TbnoticeSelectDto> saveTbnotice(@Valid @RequestBody TbnoticeCreateDto tbnoticeCreateDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(tbnoticeService.saveTbnotice(tbnoticeCreateDto));
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/{id}")
	public ResponseEntity<TbnoticeSelectDto> getTbnotice(@PathVariable("id") String id, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.getTbnotice(id));
	}
	
	@PreAuthorize("permitAll()")
	@PutMapping("")
	public ResponseEntity<TbnoticeSelectDto> updateTbnotice(@Valid @RequestBody TbnoticeUpdateDto tbnoticeUpdateDto) {
		return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.updateTbnotice(tbnoticeUpdateDto));
	}
	
	@PreAuthorize("permitAll()")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteTbnotice(@PathVariable("id") String id) {
		tbnoticeService.deleteTbnotice(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/list/all")
	public ResponseEntity<List<TbnoticeSelectDto>> getTbnoticeList() {
		return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.getTbnoticeList());
	}
	
	/*
	@PreAuthorize("permitAll()")
	*/
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/list")
	public ResponseEntity<Page<TbnoticeSelectDto>> getPagedTbnoticeList(
			@PageableDefault(size=10, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable
			,HttpServletRequest request
			) {
		
		System.out.println("tbuserId : " + request.getAttribute("tbuserId"));
		
		return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.getPagedTbnoticeList(pageable));
	}
	
	/*
	@PreAuthorize("hasRole('ROLE_USER')")
	*/
	@PreAuthorize("permitAll()")
	@GetMapping("/test")
	public ResponseEntity<List<TbnoticeSelectDto>> saveTbnotice1() {
		return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.getTbnoticeList());
	}
	
}