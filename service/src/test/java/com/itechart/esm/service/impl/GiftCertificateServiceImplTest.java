package com.itechart.esm.service.impl;

import com.itechart.esm.repository.GiftCertificateRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
	private GiftCertificateServiceImpl giftCertificateService;

	@Mock
	private GiftCertificateRepository giftCertificateRepository;

	private static final Long testId = 1L;
	private static final String name = "name";
	private static final String description = "description";

	@BeforeEach
	void setUp() {
		giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository);
	}

	@Test
	void testSave() throws DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate();

		// When
		giftCertificateService.save(giftCertificate);

		// Then
		ArgumentCaptor<GiftCertificate> tagArgumentCaptor = ArgumentCaptor.forClass(GiftCertificate.class);
		Mockito.verify(giftCertificateRepository).save(tagArgumentCaptor.capture());
		GiftCertificate capturedGiftCertificate = tagArgumentCaptor.getValue();
		Assertions.assertEquals(capturedGiftCertificate, giftCertificate);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindAll() {
		// Given
		// When
		giftCertificateService.findAll();

		// Then
		Mockito.verify(giftCertificateRepository).findAll();
	}

	@Test
	void testFindById() throws GiftCertificateNotFoundException, DataInputException {
		// Given
		Optional<GiftCertificate> expectedOptionalGiftCertificate = Optional.of(new GiftCertificate());
		Mockito.doReturn(expectedOptionalGiftCertificate).when(giftCertificateRepository).findById(testId);

		// When
		GiftCertificate actualResult = giftCertificateService.findById(testId);

		// Then
		Mockito.verify(giftCertificateRepository).findById(testId);
		Assertions.assertEquals(expectedOptionalGiftCertificate.get(), actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testFindByIdThrowsGiftCertificateNotFoundException() {
		// Given
		Mockito.doReturn(Optional.empty()).when(giftCertificateRepository).findById(testId);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.findById(testId));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByIdThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateService.findById(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testFindByPartOfName() throws DataInputException {
		// Given
		List<GiftCertificate> expectedResult = List.of(new GiftCertificate());
		Mockito.doReturn(expectedResult).when(giftCertificateRepository).findByPartOfName(name);

		// When
		List<GiftCertificate> actualResult = giftCertificateService.findByPartOfName(name);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testFindByPartOfNameThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateService.findByPartOfName(null));
	}

	@Test
	void findByPartOfDescription() throws DataInputException {
		// Given
		List<GiftCertificate> expectedResult = List.of(new GiftCertificate());
		Mockito.doReturn(expectedResult).when(giftCertificateRepository).findByPartOfDescription(description);

		// When
		List<GiftCertificate> actualResult = giftCertificateService.findByPartOfDescription(description);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByPartOfDescriptionThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateService.findByPartOfDescription(null));
	}

	@Test
	void testUpdate() throws GiftCertificateNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate();
		Mockito.doReturn(true).when(giftCertificateRepository).update(giftCertificate);

		// When
		giftCertificateService.update(giftCertificate);

		// Then
		Mockito.verify(giftCertificateRepository).update(giftCertificate);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testUpdateThrowsGiftCertificateNotFoundException() {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate();
		Mockito.doReturn(false).when(giftCertificateRepository).update(giftCertificate);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateNotFoundException.class,
				() -> giftCertificateService.update(giftCertificate));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testUpdateThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateService.update(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testDelete() throws GiftCertificateNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate();
		Mockito.doReturn(true).when(giftCertificateRepository).delete(giftCertificate);

		// When
		giftCertificateService.delete(giftCertificate);

		// Then
		Mockito.verify(giftCertificateRepository).delete(giftCertificate);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteThrowsGiftCertificateNotFoundException() {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate();
		Mockito.doReturn(false).when(giftCertificateRepository).delete(giftCertificate);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateNotFoundException.class,
				() -> giftCertificateService.delete(giftCertificate));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateService.delete(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}

	@Test
	void testDeleteById() throws GiftCertificateNotFoundException, DataInputException {
		// Given
		Mockito.doReturn(true).when(giftCertificateRepository).deleteById(testId);

		// When
		giftCertificateService.deleteById(testId);

		// Then
		Mockito.verify(giftCertificateRepository).deleteById(testId);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteByIdThrowsGiftCertificateNotFoundException() {
		// Given
		Mockito.doReturn(false).when(giftCertificateRepository).deleteById(testId);

		// When
		// Then
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertThrows(GiftCertificateNotFoundException.class,
				() -> giftCertificateService.deleteById(testId));

	}

	@Test
	void testDeleteByIdThrowsDataInputExceptionException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateService.deleteById(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
	}
}