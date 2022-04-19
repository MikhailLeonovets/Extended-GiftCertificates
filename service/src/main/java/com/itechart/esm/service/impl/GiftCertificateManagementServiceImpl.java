package com.itechart.esm.service.impl;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.GiftCertificateManagementService;
import com.itechart.esm.service.GiftCertificateService;
import com.itechart.esm.service.GiftCertificateTagService;
import com.itechart.esm.service.TagService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import com.itechart.esm.service.model.GiftCertificateAndItsTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateManagementServiceImpl implements GiftCertificateManagementService {
	private final GiftCertificateService giftCertificateService;
	private final TagService tagService;
	private final GiftCertificateTagService giftCertificateTagService;

	@Autowired
	public GiftCertificateManagementServiceImpl(GiftCertificateService giftCertificateService,
	                                            TagService tagService,
	                                            GiftCertificateTagService giftCertificateTagService) {
		this.giftCertificateService = giftCertificateService;
		this.tagService = tagService;
		this.giftCertificateTagService = giftCertificateTagService;
	}

	@Override
	public void createGiftCertificate(GiftCertificateAndItsTags giftCertificateAndItsTags)
			throws DataInputException {
		if (giftCertificateAndItsTags == null) {
			throw new DataInputException();
		}
		giftCertificateService.save(giftCertificateAndItsTags.getGiftCertificate());
		for (Tag includedTag : giftCertificateAndItsTags.getTags()) {
			tagService.save(includedTag);
			GiftCertificateTag giftCertificateTag =
					new GiftCertificateTag(giftCertificateAndItsTags.getGiftCertificate(), includedTag);
			giftCertificateTagService.save(giftCertificateTag);
		}
	}

	@Override
	public List<GiftCertificateAndItsTags> findAll()
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		List<GiftCertificateAndItsTags> giftCertificatesAndItsTags = new ArrayList<>();
		List<GiftCertificate> giftCertificates = giftCertificateService.findAll();
		if (giftCertificates.isEmpty()) {
			return giftCertificatesAndItsTags;
		}
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificatesAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificatesAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findAllSortByDateOfCreation()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificate> giftCertificates = giftCertificateService.findAll();
		List<GiftCertificate> sortedGiftCertificates = giftCertificates.stream()
				.sorted(Comparator.comparing(GiftCertificate::getDateOfCreation)).collect(Collectors.toList());
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : sortedGiftCertificates) {
			giftCertificateAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificateAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findAllSortByName()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificate> giftCertificates = giftCertificateService.findAll();
		List<GiftCertificate> sortedGiftCertificates = giftCertificates.stream()
				.sorted(Comparator.comparing(GiftCertificate::getName)).collect(Collectors.toList());
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : sortedGiftCertificates) {
			giftCertificateAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificateAndItsTags;
	}

	@Override
	public GiftCertificateAndItsTags findByGiftCertificateId(Long id) throws GiftCertificateNotFoundException,
			DataInputException, TagNotFoundException {
		if (id == null) {
			throw new DataInputException();
		}
		return createGiftCertificateAndItsTags(giftCertificateService.findById(id));
	}

	@Override
	public List<GiftCertificateAndItsTags> findByTag(Tag tag)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		if (tag == null) {
			throw new DataInputException();
		}
		List<GiftCertificateTag> giftCertificatesTags = giftCertificateTagService.findByTag(tag);
		List<GiftCertificate> giftCertificates = giftCertificatesTags.stream()
				.map(GiftCertificateTag::getGiftCertificate).collect(Collectors.toList());
		List<GiftCertificateAndItsTags> giftCertificatesAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificatesAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificatesAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findByTag(String name)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		if (name == null) {
			throw new DataInputException();
		}
		Tag tag = tagService.findByName(name);
		List<GiftCertificateTag> giftCertificatesTags = giftCertificateTagService.findByTag(tag);
		List<GiftCertificate> giftCertificates = giftCertificatesTags.stream()
				.map(GiftCertificateTag::getGiftCertificate).collect(Collectors.toList());
		List<GiftCertificateAndItsTags> giftCertificatesAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificatesAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificatesAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findByTag(Long id)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		if (id == null) {
			throw new DataInputException();
		}
		Tag tag = tagService.findById(id);
		List<GiftCertificateTag> giftCertificatesTags = giftCertificateTagService.findByTag(tag);
		List<GiftCertificate> giftCertificates = giftCertificatesTags.stream()
				.map(GiftCertificateTag::getGiftCertificate).collect(Collectors.toList());
		List<GiftCertificateAndItsTags> giftCertificatesAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificatesAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificatesAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findByPartOfName(String name)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		if (name == null) {
			throw new DataInputException();
		}
		List<GiftCertificate> giftCertificates = giftCertificateService.findByPartOfName(name);
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificateAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificateAndItsTags;
	}

	@Override
	public List<GiftCertificateAndItsTags> findByPartOfDescription(String description)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		if (description == null) {
			throw new DataInputException();
		}
		List<GiftCertificate> giftCertificates = giftCertificateService.findByPartOfDescription(description);
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			giftCertificateAndItsTags.add(createGiftCertificateAndItsTags(giftCertificate));
		}
		return giftCertificateAndItsTags;
	}

	@Override
	public void update(GiftCertificateAndItsTags giftCertificateAndItsTags)
			throws GiftCertificateNotFoundException, DataInputException, TagNotFoundException,
			GiftCertificateTagNotFoundException {
		GiftCertificate giftCertificate = giftCertificateAndItsTags.getGiftCertificate();
		giftCertificateService.update(giftCertificate);
		Set<Tag> actualTags = giftCertificateAndItsTags.getTags();
		Set<Tag> databaseTags
				= giftCertificateTagService.findByGiftCertificate(giftCertificate)
				.stream()
				.map(GiftCertificateTag::getTag).collect(Collectors.toSet());
		saveTagsAttachedToGiftCertificate(giftCertificate, actualTags, databaseTags);
		deleteTagsAttachedToGiftCertificate(giftCertificate, actualTags, databaseTags);
	}

	private void deleteTagsAttachedToGiftCertificate(GiftCertificate giftCertificate,
	                                                 Set<Tag> actualTags, Set<Tag> databaseTags)
			throws GiftCertificateTagNotFoundException, DataInputException, GiftCertificateNotFoundException,
			TagNotFoundException {
		for (Tag tagToDelete : databaseTags) {
			if (!actualTags.contains(tagToDelete)) {
				GiftCertificateTag giftCertificateTag =
						giftCertificateTagService.findByTagIdAndGiftCertificateId(tagToDelete.getId(),
								giftCertificate.getId());
				giftCertificateTagService.delete(giftCertificateTag);
			}
		}
	}

	private void saveTagsAttachedToGiftCertificate(GiftCertificate giftCertificate, Set<Tag> actualTags,
	                                               Set<Tag> databaseTags) throws DataInputException {
		for (Tag tagToAdd : actualTags) {
			if (!databaseTags.contains(tagToAdd)) {
				tagToAdd = tagService.save(tagToAdd);
				GiftCertificateTag giftCertificateTag =
						new GiftCertificateTag(giftCertificate, tagToAdd);
				giftCertificateTagService.save(giftCertificateTag);
			}
		}
	}

	@Override
	public void delete(GiftCertificateAndItsTags giftCertificateAndItsTags)
			throws GiftCertificateNotFoundException, DataInputException {
		giftCertificateService.deleteById(giftCertificateAndItsTags.getGiftCertificate().getId());
	}

	@Override
	public void deleteById(Long id) throws GiftCertificateNotFoundException, DataInputException {
		giftCertificateService.deleteById(id);
	}

	private GiftCertificateAndItsTags createGiftCertificateAndItsTags(GiftCertificate giftCertificate)
			throws DataInputException, TagNotFoundException, GiftCertificateNotFoundException {
		Set<Tag> tags =
				giftCertificateTagService.findByGiftCertificate(giftCertificate)
						.stream()
						.map(GiftCertificateTag::getTag)
						.collect(Collectors.toSet());
		return new GiftCertificateAndItsTags(giftCertificate, tags);
	}
}
