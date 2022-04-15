package com.itechart.esm.service.impl;

import com.itechart.esm.repository.TagRepository;
import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.service.exception.DataInputException;
import com.itechart.esm.service.exception.TagNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
	private TagServiceImpl tagService;

	@Mock
	private TagRepository tagRepository;

	private static final Long testId = 1L;
	private static final String testName = "name";

	@BeforeEach
	void setUp() {
		tagService = new TagServiceImpl(tagRepository);
	}

	@Test
	void testSave() throws DataInputException {
		// Given
		Tag tag = new Tag();

		// When
		tagService.save(tag);

		// Then
		ArgumentCaptor<Tag> tagArgumentCaptor = ArgumentCaptor.forClass(Tag.class);
		Mockito.verify(tagRepository).save(tagArgumentCaptor.capture());
		Tag capturedTag = tagArgumentCaptor.getValue();
		Assertions.assertEquals(capturedTag, tag);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testSaveThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> tagService.save(null));
	}

	@Test
	void testFindAll() {
		// Given
		// When
		tagService.findAll();

		// Then
		Mockito.verify(tagRepository).findAll();
	}

	@Test
	void testFindById() throws TagNotFoundException, DataInputException {
		// Given
		Optional<Tag> expectedOptionalResult = Optional.of(new Tag());
		Mockito.doReturn(expectedOptionalResult).when(tagRepository).findById(testId);

		// When
		Tag actualResult = tagService.findById(testId);

		// Then
		Assertions.assertEquals(expectedOptionalResult.get(), actualResult);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByIdThrowsTagNotFoundException() {
		// Given
		Mockito.doReturn(Optional.empty()).when(tagRepository).findById(testId);

		// When
		// Then
		Assertions.assertThrows(TagNotFoundException.class, () -> tagService.findById(testId));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByIdThrowsTagDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> tagService.findById(null));
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByName() throws TagNotFoundException, DataInputException {
		// Given
		Optional<Tag> expectedOptionalResult = Optional.of(new Tag());
		Mockito.doReturn(expectedOptionalResult).when(tagRepository).findByName(testName);

		// When
		Tag actualResult = tagService.findByName(testName);

		// Then
		Assertions.assertEquals(expectedOptionalResult.get(), actualResult);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testFindByNameThrowsTagNotFoundException() {
		// Given
		Mockito.doReturn(Optional.empty()).when(tagRepository).findByName(testName);

		// When
		// Then
		Assertions.assertThrows(TagNotFoundException.class, () -> tagService.findByName(testName));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testFindByNameThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> tagService.findByName(null));
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testDelete() throws TagNotFoundException, DataInputException {
		// Given
		Tag tag = new Tag();
		Mockito.doReturn(true).when(tagRepository).delete(tag);

		// When
		tagService.delete(tag);

		// Then
		Mockito.verify(tagRepository).delete(tag);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testDeleteThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> tagService.delete(null));
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testDeleteThrowsTagNotFoundException() {
		// Given
		Mockito.doReturn(false).when(tagRepository).deleteById(testId);

		// When
		// Then
		Assertions.assertThrows(TagNotFoundException.class, () -> tagService.deleteById(testId));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}

	@Test
	void testDeleteById() throws TagNotFoundException, DataInputException {
		// Given
		Mockito.doReturn(true).when(tagRepository).deleteById(testId);

		// When
		tagService.deleteById(testId);

		// Then
		Mockito.verify(tagRepository).deleteById(testId);
		Assertions.assertDoesNotThrow(DataInputException::new);
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testDeleteByIdThrowsDataInputException() {
		// Given
		// When
		// Then
		Assertions.assertThrows(DataInputException.class, () -> tagService.deleteById(null));
		Assertions.assertDoesNotThrow(TagNotFoundException::new);
	}

	@Test
	void testDeleteByIdThrowsTagNotFoundException() {
		// Given
		Mockito.doReturn(false).when(tagRepository).deleteById(testId);

		// When
		// Then
		Assertions.assertThrows(TagNotFoundException.class, () -> tagService.deleteById(testId));
		Assertions.assertDoesNotThrow(DataInputException::new);
	}
}