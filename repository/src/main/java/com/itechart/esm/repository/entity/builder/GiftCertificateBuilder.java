package com.itechart.esm.repository.entity.builder;

import com.itechart.esm.repository.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

public class GiftCertificateBuilder {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private LocalDateTime dateOfCreation;
	private LocalDateTime dateOfModification;
	private Period expirationPeriod;

	public GiftCertificateBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public GiftCertificateBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public GiftCertificateBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public GiftCertificateBuilder setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public GiftCertificateBuilder setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
		return this;
	}

	public GiftCertificateBuilder setDateOfModification(LocalDateTime dateOfModification) {
		this.dateOfModification = dateOfModification;
		return this;
	}

	public GiftCertificateBuilder setExpirationPeriod(Period expirationPeriod) {
		this.expirationPeriod = expirationPeriod;
		return this;
	}

	public GiftCertificate build() {
		return new GiftCertificate(id, name, description, price, dateOfCreation, dateOfModification, expirationPeriod);
	}
}