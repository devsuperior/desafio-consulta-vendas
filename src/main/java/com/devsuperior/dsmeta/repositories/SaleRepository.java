package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.ReportProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name "
                    + "FROM tb_sales "
                    + "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id "
                    + "WHERE UPPER(tb_seller.name) LIKE CONCAT('%',UPPER(:name),'%') AND "
                    + "tb_sales.date BETWEEN :minDate AND :maxDate",
                    countQuery = "SELECT COUNT(*) FROM tb_sales INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id "
                            + "WHERE UPPER(tb_seller.name) LIKE CONCAT('%',UPPER(:name),'%') AND "
                            + "tb_sales.date BETWEEN :minDate AND :maxDate")
    Page<ReportProjection> findReportNativeSQL(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(value = "SELECT obj "
                    + "FROM Sale obj "
                    + "JOIN FETCH obj.seller "
                    + "WHERE obj.date BETWEEN :minDate AND :maxDate "
                    + "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name,'%'))",
                    countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller "
                            + "WHERE obj.date BETWEEN :minDate AND :maxDate "
                            + "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name,'%'))")
    Page<Sale> findReportJPQL(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT tb_seller.name, SUM(tb_sales.amount) AS salesAmount "
                    + "FROM tb_sales "
                    + "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id "
                    + "WHERE tb_sales.date BETWEEN :minDate AND :maxDate "
                    + "GROUP BY tb_seller.name")
    List<SummaryProjection> findSummaryNativeSQL(LocalDate minDate, LocalDate maxDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
            + "FROM Sale obj "
            + "WHERE obj.date >= :minDate "
            + "AND obj.date <= :maxDate "
            + "GROUP BY obj.seller.name")
    List<SaleSummaryDTO> findSummaryJPQL(LocalDate minDate, LocalDate maxDate);

}
