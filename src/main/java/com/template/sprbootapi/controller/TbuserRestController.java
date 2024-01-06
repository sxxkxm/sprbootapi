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

import com.template.sprbootapi.data.dto.TbuserDto.TbuserAfterCreateDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserCreateDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserSelectDto;
import com.template.sprbootapi.data.dto.TbuserDto.TbuserUpdateDto;
import com.template.sprbootapi.service.TbuserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tbuser")
public class TbuserRestController {
	
	private final TbuserService tbuserService;
	
	public TbuserRestController(TbuserService tbuserService) {
		this.tbuserService = tbuserService;
	}

	@PreAuthorize("permitAll()")
	@PostMapping("")
	public ResponseEntity<TbuserAfterCreateDto> saveTbuser(@Valid @RequestBody TbuserCreateDto tbuserCreateDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.saveTbuser(tbuserCreateDto));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<TbuserSelectDto> getTbuser(@PathVariable("id") String id, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(tbuserService.getTbuser(id));
	}
	
	@PreAuthorize("permitAll()")
	@PutMapping("")
	public ResponseEntity<TbuserUpdateDto> updateTbuser(@Valid @RequestBody TbuserUpdateDto tbuserUpdateDto) {
		return ResponseEntity.status(HttpStatus.OK).body(tbuserService.updateTbuser(tbuserUpdateDto));
	}
	
	@PreAuthorize("permitAll()")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteTbuser(@PathVariable("id") String id) {
		tbuserService.deleteTbuser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@PreAuthorize("permitAll()")
	@GetMapping("/list/all")
	public ResponseEntity<List<TbuserSelectDto>> getTbuserList() {
		return ResponseEntity.status(HttpStatus.OK).body(tbuserService.getTbuserList());
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/list")
	public ResponseEntity<Page<TbuserSelectDto>> getPagedTbuserList(@PageableDefault(size=10, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(tbuserService.getPagedTbuserList(pageable));
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/mybatis/{id}")
	public ResponseEntity<TbuserSelectDto> getTbuserByMyBatis(@PathVariable("id") String id, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(tbuserService.getTbuserByMyBatis(id));
	}
	
}