package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj " +
            "WHERE obj.date BETWEEN :inicio and :fim GROUP BY obj.seller.name")
    List<SummaryMinDTO> searchBySummary(LocalDate inicio, LocalDate fim);

    @Query(value = """
        SELECT obj FROM Sale obj JOIN FETCH obj.seller
        WHERE obj.date >= :dataMinima
        AND obj.date <= :dataMaxima
        AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """, countQuery = """
        SELECT COUNT(obj) FROM Sale obj JOIN obj.seller
        WHERE obj.date >= :dataMinima
        AND obj.date <= :dataMaxima
        AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    Page<Sale> searchByReport(LocalDate dataMinima, LocalDate dataMaxima, String name, Pageable pageable);
}
