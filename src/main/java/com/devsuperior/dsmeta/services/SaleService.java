package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SummaryMinDTO> findBySummary(String dataInicial, String dataFinal) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate dataMaxima = (dataFinal == null || dataFinal.equals("")) ? today : LocalDate.parse(dataFinal);
		LocalDate dataMinima = (dataInicial == null || dataInicial.equals("")) ? dataMaxima.minusYears(1L) : LocalDate.parse(dataInicial);

		return repository.searchBySummary(dataMinima, dataMaxima);
	}

	public Page<ReportMinDTO> findByReport(String dataInicial, String dataFinal, String name, Pageable pageable){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate dataMaxima = (dataFinal == null || dataFinal.equals("")) ? today : LocalDate.parse(dataFinal);
		LocalDate dataMinima = (dataInicial == null || dataInicial.equals("")) ? dataMaxima.minusYears(1L) : LocalDate.parse(dataInicial);
		Page<Sale> result = repository.searchByReport(dataMinima, dataMaxima, name, pageable);
		return result.map(x -> new ReportMinDTO(x));
	}
}
