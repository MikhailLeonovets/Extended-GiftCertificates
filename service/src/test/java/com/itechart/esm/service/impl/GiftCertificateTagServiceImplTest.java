package com.itechart.esm.service.impl;

import com.itechart.esm.repository.GiftCertificateTagRepository;
import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GiftCertificateTagServiceImplTest {
	private GiftCertificateTagServiceImpl giftCertificateTagService;

	@Mock
	private GiftCertificateTagRepository giftCertificateTagRepository;
	@Mock
	private GiftCertificateServiceImpl giftCertificateService;
	@Mock
	private TagServiceImpl tagService;

	private static final Long testId = 1L;

	@BeforeEach
	void setUp() {
		giftCertificateTagService = new GiftCertificateTagServiceImpl(giftCertificateTagRepository,
				giftCertificateService, tagService);
	}

	@Test
	void testSave() throws DataInputException {
		// Given
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
		Mockito.doReturn(giftCertificateTag).when(giftCertificateTagRepository).save(giftCertificateTag);

		// When
		giftCertificateTagService.save(giftCertificateTag);

		// Then
		Mockito.verify(giftCertificateTagRepository).save(giftCertificateTag);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testSaveThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateTagService.save(null));
	}

	@Test
	void testFindAll() throws GiftCertificateNotFoundException, DataInputException, TagNotFoundException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId);
		Tag tag = new Tag(testId);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		List<GiftCertificateTag> expectedResult = List.of(giftCertificateTag);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId);
		Mockito.doReturn(tag).when(tagService).findById(testId);
		Mockito.doReturn(expectedResult).when(giftCertificateTagRepository).findAll();

		// When
		List<GiftCertificateTag> actualResult = giftCertificateTagService.findAll();

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindById() throws GiftCertificateNotFoundException, DataInputException,
			TagNotFoundException, GiftCertificateTagNotFoundException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId);
		Tag tag = new Tag(testId);
		Optional<GiftCertificateTag> expectedOptionalResult = Optional.of(new GiftCertificateTag(giftCertificate, tag));
		Mockito.doReturn(expectedOptionalResult).when(giftCertificateTagRepository).findById(testId);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId);
		Mockito.doReturn(tag).when(tagService).findById(testId);
		GiftCertificateTag expectedResult = expectedOptionalResult.get();

		// When
		GiftCertificateTag actualResult = giftCertificateTagService.findById(testId);

		// Then
		Assertions.assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByIdThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateTagService.findById(null));
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByTag() throws TagNotFoundException, DataInputException, GiftCertificateNotFoundException {
		// Given
		Tag tag = new Tag(testId);
		GiftCertificate giftCertificate = new GiftCertificate(testId);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		List<GiftCertificateTag> expectedResult = List.of(giftCertificateTag);
		Mockito.doReturn(expectedResult).when(giftCertificateTagRepository).findByTag(tag);
		Mockito.doReturn(tag).when(tagService).findById(testId);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId);

		// When
		List<GiftCertificateTag> actualResult = giftCertificateTagService.findByTag(tag);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByTagThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateTagService.findByTag(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByGiftCertificate() throws TagNotFoundException, DataInputException, GiftCertificateNotFoundException {
		// Given
		Tag tag = new Tag(testId);
		GiftCertificate giftCertificate = new GiftCertificate(testId);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		List<GiftCertificateTag> expectedResult = List.of(giftCertificateTag);
		Mockito.doReturn(expectedResult).when(giftCertificateTagRepository).findByGiftCertificate(giftCertificate);
		Mockito.doReturn(tag).when(tagService).findById(testId);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId);

		// When
		List<GiftCertificateTag> actualResult = giftCertificateTagService.findByGiftCertificate(giftCertificate);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByGiftCertificateThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> giftCertificateTagService.findByGiftCertificate(null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByTagIdAndGiftCertificateId() throws GiftCertificateNotFoundException,
			DataInputException, TagNotFoundException, GiftCertificateTagNotFoundException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId);
		Tag tag = new Tag(testId);
		GiftCertificateTag expectedResult = new GiftCertificateTag(giftCertificate, tag);
		Optional<GiftCertificateTag> optionalGiftCertificateTag = Optional.of(expectedResult);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId);
		Mockito.doReturn(tag).when(tagService).findById(testId);
		Mockito.doReturn(optionalGiftCertificateTag).when(giftCertificateTagRepository)
				.findByTagIdAndGiftCertificateId(testId, testId);

		// When
		GiftCertificateTag actualResult = giftCertificateTagService.findByTagIdAndGiftCertificateId(testId, testId);

		// Then
		Assertions.assertEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}

	@Test
	void testFindByTagIdAndGiftCertificateIdThrowsDataInputExceptionBothArgsNull() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateTagService.findByTagIdAndGiftCertificateId(null, null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}

	@Test
	void testFindByTagIdAndGiftCertificateIdThrowsDataInputExceptionFirstArgNull() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateTagService.findByTagIdAndGiftCertificateId(null, testId));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}

	@Test
	void testFindByTagIdAndGiftCertificateIdThrowsDataInputExceptionSecondArgNull() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateTagService.findByTagIdAndGiftCertificateId(testId, null));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}

	@Test
	void testFindByTagIdAndGiftCertificateIdThrowsGiftCertificateTagNotFoundException() {
		// Given
		Mockito.doReturn(Optional.empty()).when(giftCertificateTagRepository)
				.findByTagIdAndGiftCertificateId(testId, testId);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateTagNotFoundException.class,
				() -> giftCertificateTagService.findByTagIdAndGiftCertificateId(testId, testId));
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDelete() throws GiftCertificateTagNotFoundException, DataInputException {
		// Given
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
		Mockito.doReturn(true).when(giftCertificateTagRepository).delete(giftCertificateTag);

		// When
		giftCertificateTagService.delete(giftCertificateTag);

		// Then
		Mockito.verify(giftCertificateTagRepository).delete(giftCertificateTag);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteThrowsGiftCertificateTagNotFoundException() {
		// Given
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
		Mockito.doReturn(false).when(giftCertificateTagRepository).delete(giftCertificateTag);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateTagNotFoundException.class,
				() -> giftCertificateTagService.delete(giftCertificateTag));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateTagService.delete(null));
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}

	@Test
	void testDeleteById() throws GiftCertificateTagNotFoundException, DataInputException {
		// Given
		Mockito.doReturn(true).when(giftCertificateTagRepository).deleteById(testId);

		// When
		giftCertificateTagService.deleteById(testId);

		// Then
		Mockito.verify(giftCertificateTagRepository).deleteById(testId);
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteByIdThrowsGiftCertificateTagNotFoundException() {
		// Given
		Mockito.doReturn(false).when(giftCertificateTagRepository).deleteById(testId);

		// When
		// Then
		Assertions.assertThrows(GiftCertificateTagNotFoundException.class,
				() -> giftCertificateTagService.deleteById(testId));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteByIdThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateTagService.deleteById(null));
		Assertions.assertDoesNotThrow(GiftCertificateTagNotFoundException::new);
	}
}