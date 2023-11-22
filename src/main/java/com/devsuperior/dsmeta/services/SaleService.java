package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleReportDto;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public SaleReportDto findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDto(entity);
	}
	
	public Page<SaleReportDto> getReport(String start, String end, String sellerName, Pageable pageable) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate endDate = "".equals(end) ? today : LocalDate.parse(end, dtf);
        LocalDate startDate = "".equals(start) ? endDate.minusYears(1L) : LocalDate.parse(start, dtf);
        
        Page<Sale> result = repository.searchSales(startDate, endDate, sellerName, pageable);
        return result.map(x -> new SaleReportDto(x));
     
    }
	
	public List<SummaryDTO> getSummary(String start, String end) {
		
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate endDate = "".equals(end) ? today : LocalDate.parse(end, dtf);
        LocalDate startDate = "".equals(start) ? endDate.minusYears(1L) : LocalDate.parse(start, dtf);
		
        return repository.searchSummary(startDate, endDate);
		
	}
}
