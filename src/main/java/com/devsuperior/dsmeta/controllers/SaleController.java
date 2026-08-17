package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(value = "minDate",defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	){
		Pageable pageable = PageRequest.of(page,size);
		Page<SaleMinDTO> report = service.getReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(report);
	}

	@GetMapping("/summary")
	public ResponseEntity<List<SellerMinDTO>> getSummary(
			@RequestParam(value = "minDate", defaultValue = "") String minDate,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDate
	){
		List<SellerMinDTO> list = service.searchBySeller(minDate, maxDate);
		return ResponseEntity.ok(list);
	}
}
