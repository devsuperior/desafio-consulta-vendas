package com.devsuperior.dsmeta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportDto> findById(@PathVariable Long id) {
		SaleReportDto dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	  @GetMapping(value = "/report")
	    public ResponseEntity<Page<SaleReportDto>> getReport(
	            @RequestParam(name = "minDate", defaultValue = "") String minDate,
	            @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
	            @RequestParam(name = "name", defaultValue = "") String name,
	            Pageable pageable) {
	        return ResponseEntity.ok(service.getReport(minDate, maxDate, name, pageable));
	    }
	
	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummaryDTO>> getSummary( 
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
            @RequestParam(name = "maxDate", defaultValue = "") String maxDate) {
		
		 return ResponseEntity.ok(service.getSummary(minDate, maxDate));
	}
}
