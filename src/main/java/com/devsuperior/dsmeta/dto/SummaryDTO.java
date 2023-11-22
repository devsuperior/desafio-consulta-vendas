package com.devsuperior.dsmeta.dto;

public class SummaryDTO {
	
	private String sellerName;
	private Double total;
	
	public SummaryDTO(String sellerName, Double amount) {
		this.sellerName = sellerName;
		this.total = amount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getAmount() {
		return total;
	} 
}
