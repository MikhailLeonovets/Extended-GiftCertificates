package com.itechart.esm.controller;

import com.itechart.esm.service.GiftCertificateManagementService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import com.itechart.esm.service.model.GiftCertificateAndItsTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_SEARCH_GIFT_CERT_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SEARCH_BY_PART_OF_DESCRIPTION;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_SEARCH_BY_PART_OF_NAME;

@RequestMapping
@RestController
@PropertySource("classpath:response_msg_success.properties")
public class GiftCertificateSearchController {
	@Value("${gift_certificate.empty.by.name}")
	private String GIFT_CERT_LIST_BY_NAME_EMPTY_MSG;
	@Value("${gift_certificate.empty.by.description}")
	private String GIFT_CERT_LIST_BY_DESCRIPTION_EMPTY_MSG;

	private final GiftCertificateManagementService giftCertificateManagementService;

	@Autowired
	public GiftCertificateSearchController(GiftCertificateManagementService giftCertificateManagementService) {
		this.giftCertificateManagementService = giftCertificateManagementService;
	}

	@GetMapping(URL_MAIN_SEARCH_GIFT_CERT_PAGE + URL_SEARCH_BY_PART_OF_NAME)
	public ResponseEntity<?> getGiftCertificatesByPartOfName(@PathVariable String name)
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTagsList =
				giftCertificateManagementService.findByPartOfName(name);
		if (giftCertificateAndItsTagsList.isEmpty()) {
			return ResponseEntity.ok(GIFT_CERT_LIST_BY_NAME_EMPTY_MSG);
		}
		return ResponseEntity.ok(giftCertificateAndItsTagsList);
	}

	@GetMapping(URL_MAIN_SEARCH_GIFT_CERT_PAGE + URL_SEARCH_BY_PART_OF_DESCRIPTION)
	public ResponseEntity<?> getGiftCertificatesByPartOfDescription(@PathVariable String description)
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTagsList =
				giftCertificateManagementService.findByPartOfDescription(description);
		if (giftCertificateAndItsTagsList.isEmpty()) {
			return ResponseEntity.ok(GIFT_CERT_LIST_BY_DESCRIPTION_EMPTY_MSG);
		}
		return ResponseEntity.ok(giftCertificateAndItsTagsList);
	}
}
