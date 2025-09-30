package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.projections.SaleMinProjection;
import com.devsuperior.dsmeta.projections.SellerMinProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(
            nativeQuery = true,
            value = """
            SELECT tb_sales.id AS id,
                   tb_sales.date AS date,
                   tb_sales.amount AS amount,
                   tb_seller.name AS sellerName
            FROM tb_sales
            INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id
            WHERE tb_sales.date BETWEEN :minDate AND :maxDate
              AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM tb_sales
            INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id
            WHERE tb_sales.date BETWEEN :minDate AND :maxDate
              AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))
            """
    )
    Page<SaleMinProjection> searchSale(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT tb_seller.name as sellerName, SUM(amount) AS total " +
                    "FROM tb_sales " +
                    "INNER JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
                    "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY tb_seller.name " +
                    "ORDER BY name")
    Page<SellerMinProjection> searchBySeller(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}

