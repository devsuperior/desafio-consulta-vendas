package com.devsuperior.dsmeta.dto;


import com.devsuperior.dsmeta.projections.SummaryProjection;

public class SaleSummaryDTO {

    private String sellerName;
    private Double amount;

    public SaleSummaryDTO(String sellerName, Double amount) {
        this.sellerName = sellerName;
        this.amount = amount;
    }

    public SaleSummaryDTO(SummaryProjection projection) {
        sellerName = projection.getName();
        amount = projection.getSalesAmount();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getAmount() {
        return amount;
    }
}

