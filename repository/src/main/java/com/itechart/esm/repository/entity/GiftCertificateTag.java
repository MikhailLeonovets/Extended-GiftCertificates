package com.itechart.esm.repository.entity;

import java.io.Serializable;
import java.util.Objects;

public class GiftCertificateTag implements Serializable {
	private Long id;
	private GiftCertificate giftCertificate;
	private Tag tag;

	public GiftCertificateTag() {
	}

	public GiftCertificateTag(GiftCertificate giftCertificate, Tag tag) {
		this.giftCertificate = giftCertificate;
		this.tag = tag;
	}

	public GiftCertificateTag(Long id, GiftCertificate giftCertificate, Tag tag) {
		this.id = id;
		this.giftCertificate = giftCertificate;
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GiftCertificate getGiftCertificate() {
		return giftCertificate;
	}

	public void setGiftCertificate(GiftCertificate giftCertificate) {
		this.giftCertificate = giftCertificate;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GiftCertificateTag that = (GiftCertificateTag) o;
		return giftCertificate.equals(that.giftCertificate) && tag.equals(that.tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(giftCertificate, tag);
	}

	@Override
	public String toString() {
		return "GiftCertificateTag{" +
				"id=" + id +
				", giftCertificate=" + giftCertificate +
				", tag=" + tag +
				'}';
	}
}
