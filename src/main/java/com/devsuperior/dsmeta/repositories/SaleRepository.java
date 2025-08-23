package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleWithSellerDTO;
import com.devsuperior.dsmeta.dto.iSalesSumary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :min AND :max AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:sellerName,'%')) ORDER BY obj.seller.name DESC")
    Page<SaleWithSellerDTO> searchSales(LocalDate min, LocalDate max, String sellerName, Pageable pageable);
    @Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :min AND :max ORDER BY obj.seller.name DESC")
    Page<SaleWithSellerDTO> searchSales(LocalDate min, LocalDate max, Pageable pageable);

    @Query("SELECT SUM(obj.amount) as amount , obj.seller.name as sellerName FROM Sale obj WHERE obj.date BETWEEN :min AND :max GROUP BY obj.seller.name")
    List<iSalesSumary> summarySales(LocalDate min, LocalDate max);

}
