package com.itechart.esm.service.model;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.Tag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GiftCertificateAndItsTags implements Serializable {
	private GiftCertificate giftCertificate;
	private Set<Tag> tags;

	public GiftCertificateAndItsTags() {
		tags = new HashSet<>();
	}

	public GiftCertificateAndItsTags(GiftCertificate giftCertificate) {
		this.giftCertificate = giftCertificate;
		tags = new HashSet<>();
	}

	public GiftCertificateAndItsTags(GiftCertificate giftCertificate, Set<Tag> tags) {
		this.giftCertificate = giftCertificate;
		this.tags = tags;
	}

	public GiftCertificate getGiftCertificate() {
		return giftCertificate;
	}

	public void setGiftCertificate(GiftCertificate giftCertificate) {
		this.giftCertificate = giftCertificate;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public void addTags(Set<Tag> tags) {
		this.tags.addAll(tags);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GiftCertificateAndItsTags that = (GiftCertificateAndItsTags) o;
		return giftCertificate.equals(that.giftCertificate) && Objects.equals(tags, that.tags);
	}

	@Override
	public int hashCode() {
		return Objects.hash(giftCertificate, tags);
	}

	@Override
	public String toString() {
		return "GiftCertificateAndItsTags{" +
				"giftCertificate=" + giftCertificate +
				", tags=" + tags +
				'}';
	}
}
