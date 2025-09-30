package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> getReport(String minDate, String maxDate,String sellerName, Pageable pageable) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate minDateFormatted = (minDate == null || minDate.isBlank())
				? LocalDate.now().minusYears(1)
				:LocalDate.parse(minDate, formatter);


		LocalDate maxDateFormatted = (maxDate == null || maxDate.isBlank())
				? LocalDate.now()
				:LocalDate.parse(maxDate, formatter).plusDays(1);


		Page<SaleMinProjection> result = repository.searchSale(minDateFormatted, maxDateFormatted, sellerName, pageable);
		return result.map(SaleMinDTO::new);
	}

	public Page<SellerMinDTO> searchBySeller(String minDate, String maxDate, Pageable pageable) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate minDateFormatted = (minDate == null || minDate.isBlank())
				? LocalDate.now().minusYears(1)
				:LocalDate.parse(minDate, formatter);


		LocalDate maxDateFormatted = (maxDate == null || maxDate.isBlank())
				? LocalDate.now()
				:LocalDate.parse(maxDate, formatter);

		Page<SellerMinProjection> result = repository.searchBySeller(minDateFormatted, maxDateFormatted, pageable);
		return result.map(SellerMinDTO::new);
	}

	public SaleMinDTO findById(Long id) {
		return repository.findById(id)
				.map(SaleMinDTO::new)
				.orElseThrow(() -> new RuntimeException("Sale not found: " + id));
	}

}
