package com.itechart.esm.service.impl;

import com.itechart.esm.repository.GiftCertificateTagRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.GiftCertificateService;
import com.itechart.esm.service.GiftCertificateTagService;
import com.itechart.esm.service.TagService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
	private final GiftCertificateTagRepository giftCertificateTagRepository;
	private final GiftCertificateService giftCertificateService;
	private final TagService tagService;

	@Autowired
	public GiftCertificateTagServiceImpl(GiftCertificateTagRepository giftCertificateTagRepository,
	                                     GiftCertificateService giftCertificateService,
	                                     TagService tagService) {
		this.giftCertificateTagRepository = giftCertificateTagRepository;
		this.giftCertificateService = giftCertificateService;
		this.tagService = tagService;
	}

	@Override
	public void save(GiftCertificateTag giftCertificateTag) throws DataInputException {
		if (giftCertificateTag == null) {
			throw new DataInputException();
		}
		giftCertificateTagRepository.save(giftCertificateTag);
	}

	@Override
	public List<GiftCertificateTag> findAll()
			throws GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		List<GiftCertificateTag> giftCertificateTags =
				giftCertificateTagRepository.findAll();
		List<GiftCertificateTag> populatedGiftCertificateTags = new ArrayList<>();
		for (GiftCertificateTag giftCertificateTag : giftCertificateTags) {
			GiftCertificateTag fullGiftCertificateTag = populate(giftCertificateTag);
			populatedGiftCertificateTags.add(fullGiftCertificateTag);
		}
		return populatedGiftCertificateTags;
	}

	@Override
	public GiftCertificateTag findById(Long id) throws GiftCertificateTagNotFoundException, DataInputException,
			GiftCertificateNotFoundException, TagNotFoundException {
		if (id == null) {
			throw new DataInputException();
		}
		GiftCertificateTag giftCertificateTag =
				giftCertificateTagRepository.findById(id).orElseThrow(GiftCertificateTagNotFoundException::new);
		return populate(giftCertificateTag);
	}

	@Override
	public List<GiftCertificateTag> findByTag(Tag tag)
			throws DataInputException, GiftCertificateNotFoundException, TagNotFoundException {
		if (tag == null) {
			throw new DataInputException();
		}
		List<GiftCertificateTag> giftCertificateTags =
				giftCertificateTagRepository.findByTag(tag);
		List<GiftCertificateTag> populatedGiftCertificatesTag = new ArrayList<>();
		for (GiftCertificateTag giftCertificateTag : giftCertificateTags) {
			GiftCertificateTag fullGiftCertificateTag = populate(giftCertificateTag);
			populatedGiftCertificatesTag.add(fullGiftCertificateTag);
		}
		return populatedGiftCertificatesTag;
	}

	@Override
	public List<GiftCertificateTag> findByGiftCertificate(GiftCertificate giftCertificate)
			throws DataInputException, GiftCertificateNotFoundException, TagNotFoundException {
		if (giftCertificate == null) {
			throw new DataInputException();
		}
		List<GiftCertificateTag> giftCertificateTags =
				giftCertificateTagRepository.findByGiftCertificate(giftCertificate);
		List<GiftCertificateTag> populatedGiftCertificateTags = new ArrayList<>();
		for (GiftCertificateTag giftCertificateTag : giftCertificateTags) {
			GiftCertificateTag fullGiftCertificateTag = populate(giftCertificateTag);
			populatedGiftCertificateTags.add(fullGiftCertificateTag);
		}
		return populatedGiftCertificateTags;
	}

	@Override
	public GiftCertificateTag findByTagIdAndGiftCertificateId(Long tagId, Long giftCertificateId)
			throws GiftCertificateTagNotFoundException,
			GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		if (tagId == null || giftCertificateId == null) {
			throw new DataInputException();
		}
		Optional<GiftCertificateTag> optionalGiftCertificateTag =
				giftCertificateTagRepository.findByTagIdAndGiftCertificateId(tagId, giftCertificateId);
		if (optionalGiftCertificateTag.isEmpty()) {
			throw new GiftCertificateTagNotFoundException();
		}
		return populate(optionalGiftCertificateTag.get());
	}

	@Override
	public void delete(GiftCertificateTag giftCertificateTag)
			throws GiftCertificateTagNotFoundException, DataInputException {
		if (giftCertificateTag == null) {
			throw new DataInputException();
		}
		boolean isDeleted = giftCertificateTagRepository.delete(giftCertificateTag);
		if (!isDeleted) {
			throw new GiftCertificateTagNotFoundException();
		}
	}

	@Override
	public void deleteById(Long id) throws GiftCertificateTagNotFoundException, DataInputException {
		if (id == null) {
			throw new DataInputException();
		}
		boolean isDeleted = giftCertificateTagRepository.deleteById(id);
		if (!isDeleted) {
			throw new GiftCertificateTagNotFoundException();
		}
	}

	private GiftCertificateTag populate(GiftCertificateTag giftCertificateTag)
			throws GiftCertificateNotFoundException, DataInputException, TagNotFoundException {
		Long giftCertificateTagId = giftCertificateTag.getGiftCertificate().getId();
		giftCertificateTag.setGiftCertificate(giftCertificateService.findById(giftCertificateTagId));
		Long tagId = giftCertificateTag.getTag().getId();
		giftCertificateTag.setTag(tagService.findById(tagId));
		return giftCertificateTag;
	}
}
