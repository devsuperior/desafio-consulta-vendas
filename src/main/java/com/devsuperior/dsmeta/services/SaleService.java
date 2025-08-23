package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleWithSellerDTO;
import com.devsuperior.dsmeta.dto.SalesSumDTO;
import com.devsuperior.dsmeta.dto.iSalesSumary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {
	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleWithSellerDTO> searchSales(String min, String max, String name, Pageable pageable) {
		LocalDate minDate = convertStringToDate(min, "min");
		LocalDate maxDate = convertStringToDate(max, "max");
		if(name == null || name.isEmpty()) {
			return repository.searchSales(minDate, maxDate,pageable);
		}
		return repository.searchSales(minDate, maxDate,name,pageable);
	}

	public List<SalesSumDTO> summarySales(String min, String max) {
		LocalDate minDate = convertStringToDate(min, "min");
		LocalDate maxDate = convertStringToDate(max, "max");
		System.out.println(minDate + " " + maxDate);
		List<iSalesSumary> result = repository.summarySales(minDate, maxDate);
		return result.stream().map(x -> new SalesSumDTO(x)).toList();
	}
	/**
	 * Converts a string date to a LocalDate object.
	 *
	 * @param date The date string to be converted.
	 * @param type The type of date conversion. Can be "min" for minimum date or any other value for current date.
	 * @return The converted LocalDate object.
	 */
	private LocalDate convertStringToDate(String date, String type) {
		// Define the date format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Get the current date and time
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		// Check the type of date conversion
		if (Objects.equals(type, "min")) {
			// If type is "min", return the date one year ago if the input date is null or empty
			return date == null || date.isEmpty() ? today.minusYears(1L) : LocalDate.parse(date, formatter);
		} else {
			// If type is not "min", return the current date if the input date is null or empty
			return date == null || date.isEmpty() ? today : LocalDate.parse(date, formatter);
		}
	}

}
