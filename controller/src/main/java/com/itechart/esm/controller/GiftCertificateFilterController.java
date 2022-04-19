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

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_GIFT_CERT_BY_TAG_ID_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_FILTER_PAGE;

@RestController
@RequestMapping
@PropertySource("classpath:response_msg_success.properties")
public class GiftCertificateFilterController {
	@Value("${gift_certificate.empty.by.tag}")
	private String GIFT_CERT_LIST_BY_TAG_EMPTY_MSG;

	private final GiftCertificateManagementService giftCertificateManagementService;

	@Autowired
	public GiftCertificateFilterController(GiftCertificateManagementService giftCertificateManagementService) {
		this.giftCertificateManagementService = giftCertificateManagementService;
	}

	@GetMapping(URL_MAIN_GIFT_CERT_FILTER_PAGE + URL_GIFT_CERT_BY_TAG_ID_PAGE)
	public ResponseEntity<?> getGiftCertificateByTag(@PathVariable Long id) throws TagNotFoundException,
			GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = giftCertificateManagementService.findByTag(id);
		if (giftCertificateAndItsTags.isEmpty()) {
			return ResponseEntity.ok(GIFT_CERT_LIST_BY_TAG_EMPTY_MSG);
		}
		return ResponseEntity.ok(giftCertificateAndItsTags);
	}
}
