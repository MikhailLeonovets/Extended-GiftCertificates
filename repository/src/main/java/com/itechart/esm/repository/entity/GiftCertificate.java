package com.itechart.esm.repository.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

public class GiftCertificate implements Serializable {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private LocalDateTime dateOfCreation;
	private LocalDateTime dateOfModification;
	private Period expirationPeriod;

	public GiftCertificate() {
	}

	public GiftCertificate(Long id, String name, String description,
	                       BigDecimal price, LocalDateTime dateOfCreation,
	                       LocalDateTime dateOfModification, Period expirationPeriod) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.dateOfCreation = dateOfCreation;
		this.dateOfModification = dateOfModification;
		this.expirationPeriod = expirationPeriod;
	}

	public GiftCertificate(Long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(LocalDateTime dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public LocalDateTime getDateOfModification() {
		return dateOfModification;
	}

	public void setDateOfModification(LocalDateTime dateOfModification) {
		this.dateOfModification = dateOfModification;
	}

	public Period getExpirationPeriod() {
		return expirationPeriod;
	}

	public void setExpirationPeriod(Period expirationPeriod) {
		this.expirationPeriod = expirationPeriod;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GiftCertificate that = (GiftCertificate) o;
		return name.equals(that.name)
				&& description.equals(that.description)
				&& price.equals(that.price)
				&& dateOfCreation.equals(that.dateOfCreation)
				&& Objects.equals(dateOfModification, that.dateOfModification)
				&& expirationPeriod.equals(that.expirationPeriod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, price, dateOfCreation, dateOfModification, expirationPeriod);
	}

	@Override
	public String toString() {
		return "GiftCertificate{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", dateOfCreation=" + dateOfCreation +
				", dateOFModification=" + dateOfModification +
				", expirationPeriod=" + expirationPeriod +
				'}';
	}


}
