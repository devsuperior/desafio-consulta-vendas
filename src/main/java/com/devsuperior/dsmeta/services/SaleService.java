package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;


	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SaleSummaryDTO> getSummary(String minDate, String maxDate){

		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		LocalDate min = minDate.equals("") ? max.minusYears(1L) : LocalDate.parse(minDate);
		List<SummaryProjection> result = repository.findSummaryNativeSQL(min, max);
		return result.stream().map(x -> new SaleSummaryDTO(x)).toList();

	}

	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate max = maxDate.equals("") ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate min = minDate.equals("") ? max.minusYears(1L) : LocalDate.parse(minDate);

		
		Page<Sale> result1 = repository.findReportJPQL(min, max, name, pageable);
		return result1.map(x -> new SaleReportDTO(x));

	}

}
