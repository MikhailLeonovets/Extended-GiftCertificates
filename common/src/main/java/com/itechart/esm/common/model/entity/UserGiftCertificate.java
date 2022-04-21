package com.itechart.esm.common.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class UserGiftCertificate implements Serializable {
	private Long id;
	private User user;
	private GiftCertificate giftCertificate;
	private BigDecimal price;

	public UserGiftCertificate() {
	}

	public UserGiftCertificate(Long id) {
		this.id = id;
	}

	public UserGiftCertificate(Long id, Long userId, Long giftCertificateId, BigDecimal price) {
		this.id = id;
		this.user = new User(userId);
		this.giftCertificate = new GiftCertificate(giftCertificateId);
		this.price = price;
	}

	public UserGiftCertificate(Long id, User user, GiftCertificate giftCertificate, BigDecimal price) {
		this.id = id;
		this.user = user;
		this.giftCertificate = giftCertificate;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GiftCertificate getGiftCertificate() {
		return giftCertificate;
	}

	public void setGiftCertificate(GiftCertificate giftCertificate) {
		this.giftCertificate = giftCertificate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserGiftCertificate that = (UserGiftCertificate) o;
		return id.equals(that.id)
				&& user.equals(that.user)
				&& giftCertificate.equals(that.giftCertificate)
				&& price.equals(that.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, giftCertificate, price);
	}

	@Override
	public String toString() {
		return "UserGiftCertificate{" +
				"id=" + id +
				", user=" + user +
				", giftCertificate=" + giftCertificate +
				", price=" + price +
				'}';
	}
}
