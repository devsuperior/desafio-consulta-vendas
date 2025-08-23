package com.devsuperior.dsmeta.dto;

public class SalesSumDTO {
    private String sellerName;
    private Double amount;

    public SalesSumDTO(String sellerName, Double amount) {
        this.sellerName = sellerName;
        this.amount = amount;
    }

    public SalesSumDTO(iSalesSumary salesSumary) {
        this(salesSumary.getSellerName(), salesSumary.getAmount());
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getAmount() {
        return amount;
    }
}
