package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public class SummaryMinDTO {
    private String sellerName;
    private Double total;

    public SummaryMinDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SummaryMinDTO() {
    }
    public SummaryMinDTO(SaleSummaryProjection entity) {
        sellerName = entity.sellerName();
        total = entity.total();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
