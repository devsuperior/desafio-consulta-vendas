package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT obj "
			+ " FROM Sale obj "
			+ "WHERE obj.date > :minimunDate "
			+ "AND obj.date < :maximumDate "
			+ "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:sellerName,'%')) ")
    Page<Sale> searchSales(LocalDate minimunDate, LocalDate maximumDate, String sellerName, Pageable pageable);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY obj.seller.name ")
	List<SummaryDTO> searchSummary(LocalDate minDate, LocalDate maxDate);
}
