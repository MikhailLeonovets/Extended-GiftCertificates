package com.itechart.esm.service.impl;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.GiftCertificateTag;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.repository.entity.builder.GiftCertificateBuilder;
import com.itechart.esm.service.GiftCertificateService;
import com.itechart.esm.service.GiftCertificateTagService;
import com.itechart.esm.service.TagService;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.GiftCertificateNotFoundException;
import com.itechart.esm.service.exception.GiftCertificateTagNotFoundException;
import com.itechart.esm.service.exception.TagNotFoundException;
import com.itechart.esm.service.model.GiftCertificateAndItsTags;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class GiftCertificateManagementServiceImplTest {
	private GiftCertificateManagementServiceImpl giftCertificateManagementService;

	@Mock
	private GiftCertificateService giftCertificateService;
	@Mock
	private TagService tagService;
	@Mock
	private GiftCertificateTagService giftCertificateTagService;

	private static final Long testId1 = 1L;
	private static final Long testId2 = 2L;
	private static final String testName1 = "name1";
	private static final String testName2 = "name2";
	private static final String testDescription = "descrption";
	private static final LocalDateTime testDate1 = LocalDateTime.of(2015,
			Month.JULY, 29, 19, 30, 40);
	private static final LocalDateTime testDate2 = LocalDateTime.of(2020,
			Month.AUGUST, 29, 19, 30, 40);

	@BeforeEach
	void setUp() {
		giftCertificateManagementService = new GiftCertificateManagementServiceImpl(giftCertificateService,
				tagService, giftCertificateTagService);
	}

	@Test
	void testCreateGiftCertificate() throws DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		Set<Tag> itsTags = Set.of(tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate, itsTags);

		// When
		giftCertificateManagementService.createGiftCertificate(giftCertificateAndItsTags);

		// Then
		Mockito.verify(giftCertificateService).save(giftCertificate);
		Mockito.verify(tagService).save(tag);
		Mockito.verify(giftCertificateTagService).save(giftCertificateTag);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testCreateGiftCertificateThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateManagementService.createGiftCertificate(null));
	}

	@Test
	void testFindAll() throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		Set<Tag> itsTags = Set.of(tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate, itsTags);
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);
		Mockito.doReturn(List.of(giftCertificateTag)).when(giftCertificateTagService)
				.findByGiftCertificate(giftCertificate);
		Mockito.doReturn(List.of(giftCertificate)).when(giftCertificateService).findAll();

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findAll();

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void testFindAllSortByDateOfCreation()
			throws GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate1 = new GiftCertificateBuilder().setId(testId1)
				.setDateOfCreation(testDate1)
				.setName(testName1)
				.build();
		GiftCertificate giftCertificate2 = new GiftCertificateBuilder().setId(testId2)
				.setDateOfCreation(testDate2)
				.setName(testName2)
				.build();
		Tag tag1 = new Tag(testId1);
		Tag tag2 = new Tag(testId2);
		GiftCertificateTag giftCertificateTag1 = new GiftCertificateTag(giftCertificate1, tag1);
		Mockito.doReturn(List.of(giftCertificateTag1))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate1);
		GiftCertificateTag giftCertificateTag2 = new GiftCertificateTag(giftCertificate2, tag2);
		Mockito.doReturn(List.of(giftCertificateTag2))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate2);
		List<GiftCertificate> testGiftCertificates = List.of(giftCertificate2, giftCertificate1);
		Mockito.doReturn(testGiftCertificates).when(giftCertificateService).findAll();
		GiftCertificateAndItsTags giftCertificateAndItsTags1 = new GiftCertificateAndItsTags(giftCertificate1,
				Set.of(tag1));
		GiftCertificateAndItsTags giftCertificateAndItsTags2 = new GiftCertificateAndItsTags(giftCertificate2,
				Set.of(tag2));
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags1,
				giftCertificateAndItsTags2);

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findAllSortByDateOfCreation();

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindAllSortByName() throws TagNotFoundException, GiftCertificateNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate1 = new GiftCertificateBuilder().setId(testId1)
				.setDateOfCreation(testDate1)
				.setName(testName1)
				.build();
		GiftCertificate giftCertificate2 = new GiftCertificateBuilder().setId(testId2)
				.setDateOfCreation(testDate2)
				.setName(testName2)
				.build();
		Tag tag1 = new Tag(testId1);
		Tag tag2 = new Tag(testId2);
		GiftCertificateTag giftCertificateTag1 = new GiftCertificateTag(giftCertificate1, tag1);
		Mockito.doReturn(List.of(giftCertificateTag1))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate1);
		GiftCertificateTag giftCertificateTag2 = new GiftCertificateTag(giftCertificate2, tag2);
		Mockito.doReturn(List.of(giftCertificateTag2))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate2);
		List<GiftCertificate> testGiftCertificates = List.of(giftCertificate2, giftCertificate1);
		Mockito.doReturn(testGiftCertificates).when(giftCertificateService).findAll();
		GiftCertificateAndItsTags giftCertificateAndItsTags1 = new GiftCertificateAndItsTags(giftCertificate1,
				Set.of(tag1));
		GiftCertificateAndItsTags giftCertificateAndItsTags2 = new GiftCertificateAndItsTags(giftCertificate2,
				Set.of(tag2));
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags1,
				giftCertificateAndItsTags2);

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findAllSortByName();

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(GiftCertificateNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByGiftCertificateId() throws GiftCertificateNotFoundException,
			TagNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags expectedResult = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);
		Mockito.doReturn(giftCertificate).when(giftCertificateService).findById(testId1);

		// When
		GiftCertificateAndItsTags actualResult = giftCertificateManagementService.findByGiftCertificateId(testId1);

		// Then
		Assertions.assertEquals(expectedResult, actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByGiftCertificateIdThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateManagementService.findByGiftCertificateId(null));
	}

	@Test
	void testFindByTag() throws GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		Mockito.doReturn(List.of(giftCertificateTag)).when(giftCertificateTagService).findByTag(tag);
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findByTag(tag);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByTagName() throws GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1, testName1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		Mockito.doReturn(tag).when(tagService).findByName(testName1);
		Mockito.doReturn(List.of(giftCertificateTag)).when(giftCertificateTagService).findByTag(tag);
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findByTag(testName1);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByTagId() throws GiftCertificateNotFoundException, TagNotFoundException, DataInputException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificate(testId1);
		Tag tag = new Tag(testId1, testName1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		Mockito.doReturn(tag).when(tagService).findById(testId1);
		Mockito.doReturn(List.of(giftCertificateTag)).when(giftCertificateTagService).findByTag(tag);
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);

		// When
		List<GiftCertificateAndItsTags> actualResult = giftCertificateManagementService.findByTag(testId1);

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void testFindByTagThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateManagementService.findByTag((Long) null));
	}

	@Test
	void findByPartOfName() throws DataInputException, GiftCertificateNotFoundException, TagNotFoundException {
		// Given
		GiftCertificate giftCertificate = new GiftCertificateBuilder().setId(testId1).setName(testName1).build();
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);
		int numberOfSymbol = 2;
		Mockito.doReturn(List.of(giftCertificate))
				.when(giftCertificateService).findByPartOfName(testName1.substring(numberOfSymbol));
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);

		// When
		List<GiftCertificateAndItsTags> actualResult =
				giftCertificateManagementService.findByPartOfName(testName1.substring(numberOfSymbol));

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void findByPartOfNameThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateManagementService.findByPartOfName(null));
	}

	@Test
	void findByPartOfDescription() throws DataInputException, GiftCertificateNotFoundException, TagNotFoundException {
		// Given
		GiftCertificate giftCertificate =
				new GiftCertificateBuilder().setId(testId1).setName(testName1).setDescription(testDescription).build();
		Tag tag = new Tag(testId1);
		GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificate, tag);
		GiftCertificateAndItsTags giftCertificateAndItsTags = new GiftCertificateAndItsTags(giftCertificate,
				Set.of(tag));
		List<GiftCertificateAndItsTags> expectedResult = List.of(giftCertificateAndItsTags);
		int numberOfSymbol = 2;
		Mockito.doReturn(List.of(giftCertificate))
				.when(giftCertificateService).findByPartOfDescription(testDescription.substring(numberOfSymbol));
		Mockito.doReturn(List.of(giftCertificateTag))
				.when(giftCertificateTagService).findByGiftCertificate(giftCertificate);

		// When
		List<GiftCertificateAndItsTags> actualResult =
				giftCertificateManagementService.findByPartOfDescription(testDescription.substring(numberOfSymbol));

		// Then
		Assertions.assertIterableEquals(expectedResult, actualResult);
	}

	@Test
	void findByPartOfDescriptionThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class,
				() -> giftCertificateManagementService.findByPartOfDescription(null));
	}
}