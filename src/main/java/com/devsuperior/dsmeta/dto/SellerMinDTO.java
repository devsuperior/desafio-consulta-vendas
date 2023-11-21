package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;

public class SellerMinDTO {
	
	private Long id; 
	private String name;
	
	public SellerMinDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	public SellerMinDTO(Seller seller) {
		id = seller.getId();
		name = seller.getName();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
