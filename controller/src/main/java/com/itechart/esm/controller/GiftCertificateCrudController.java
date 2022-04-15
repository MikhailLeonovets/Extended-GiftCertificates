package com.itechart.esm.controller;

import com.itechart.esm.service.GiftCertificateManagementService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import com.itechart.esm.service.model.GiftCertificateAndItsTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_CREATE_GIFT_CERT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_DELETE_GIFT_CERT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_GET_ALL_GIFT_CERT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_GET_BY_ID_GIFT_CERT;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_GIFT_CERT_SORT_DATE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_GIFT_CERT_SORT_NAME;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_MAIN_GIFT_CERT_PAGE;
import static com.itechart.esm.controller.storage.url.GiftCertificateUrl.URL_UPDATE_GIFT_CERT;

@RestController
@RequestMapping
@PropertySource("classpath:response_msg_success.properties")
public class GiftCertificateCrudController {

	@Value("${gift_certificate.deleted}")
	private String giftCertificateDeletedSuccessfully;
	@Value("${gift_certificate.created}")
	private String giftCertificateCreatedSuccessfully;
	@Value("${gift_certificate.updated}")
	private String giftCertificateUpdatedSuccessfully;
	@Value("${gift_certificate.empty_list}")
	private String giftCertificateEmptyListMsg;

	private final GiftCertificateManagementService giftCertificateManagementService;

	@Autowired
	public GiftCertificateCrudController(GiftCertificateManagementService giftCertificateManagementService) {
		this.giftCertificateManagementService = giftCertificateManagementService;
	}

	@GetMapping(value = URL_MAIN_GIFT_CERT_PAGE + URL_GET_ALL_GIFT_CERT, produces = "application/json")
	public ResponseEntity<?> getGiftCertificates() throws TagNotFoundException,
			GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags = giftCertificateManagementService.findAll();
		if (giftCertificateAndItsTags.isEmpty()) {
			return ResponseEntity.ok(giftCertificateEmptyListMsg);
		}
		return ResponseEntity.ok(giftCertificateManagementService.findAll());
	}

	@GetMapping(URL_MAIN_GIFT_CERT_PAGE + URL_GET_BY_ID_GIFT_CERT)
	public ResponseEntity<?> getGiftCertificate(@PathVariable Long id) throws GiftCertificateNotFoundException,
			TagNotFoundException, DataInputException {
		return ResponseEntity.ok(giftCertificateManagementService.findByGiftCertificateId(id));
	}

	@DeleteMapping(URL_MAIN_GIFT_CERT_PAGE + URL_DELETE_GIFT_CERT)
	public ResponseEntity<?> deleteById(@PathVariable Long id) throws GiftCertificateNotFoundException,
			DataInputException {
		giftCertificateManagementService.deleteById(id);
		return ResponseEntity.ok(giftCertificateDeletedSuccessfully);
	}

	@PostMapping(URL_MAIN_GIFT_CERT_PAGE + URL_CREATE_GIFT_CERT)
	public ResponseEntity<?> createGiftCertificate(@RequestBody GiftCertificateAndItsTags giftCertificateAndItsTags)
			throws DataInputException {
		giftCertificateManagementService.createGiftCertificate(giftCertificateAndItsTags);
		return ResponseEntity.ok(giftCertificateCreatedSuccessfully);
	}

	@PutMapping(URL_MAIN_GIFT_CERT_PAGE + URL_UPDATE_GIFT_CERT)
	public ResponseEntity<?> updateGiftCertificate(@RequestBody GiftCertificateAndItsTags giftCertificateAndItsTags,
	                                               @PathVariable String id)
			throws GiftCertificateNotFoundException, TagNotFoundException, GiftCertificateTagNotFoundException,
			DataInputException {
		giftCertificateManagementService.update(giftCertificateAndItsTags);
		return ResponseEntity.ok(giftCertificateUpdatedSuccessfully);
	}

	@GetMapping(URL_MAIN_GIFT_CERT_PAGE + URL_GIFT_CERT_SORT_DATE)
	public ResponseEntity<?> getGiftCertificatesSortByDate()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTagsList =
				giftCertificateManagementService.findAllSortByDateOfCreation();
		if (giftCertificateAndItsTagsList.isEmpty()) {
			return ResponseEntity.ok(giftCertificateEmptyListMsg);
		}
		return ResponseEntity.ok(giftCertificateAndItsTagsList);
	}

	@GetMapping(URL_MAIN_GIFT_CERT_PAGE + URL_GIFT_CERT_SORT_NAME)
	public ResponseEntity<?> getGiftCertificatesSortByName()
			throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		List<GiftCertificateAndItsTags> giftCertificateAndItsTags =
				giftCertificateManagementService.findAllSortByName();
		if (giftCertificateAndItsTags.isEmpty()) {
			return ResponseEntity.ok(giftCertificateEmptyListMsg);
		}
		return ResponseEntity.ok(giftCertificateAndItsTags);
	}
}
