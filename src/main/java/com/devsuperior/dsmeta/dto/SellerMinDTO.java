package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projections.SellerMinProjection;

public class SellerMinDTO {
    private String sellerName;
    private Double total;

    public SellerMinDTO() {
    }

    public SellerMinDTO(String name, Double amount) {
        this.sellerName = name;
        this.total = amount;
    }

    public SellerMinDTO(SellerMinProjection projection) {
        this.sellerName = projection.getSellerName();
        this.total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
