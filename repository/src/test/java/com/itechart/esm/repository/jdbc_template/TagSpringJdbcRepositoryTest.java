package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.repository.entity.Tag;
import com.itechart.esm.repository.jdbc_template.config.TestSpringJdbcConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfiguration.class)
class TagSpringJdbcRepositoryTest {
	private final JdbcTemplate jdbcTemplate;

	private TagSpringJdbcRepository tagSpringJdbcRepository;

	@Autowired
	TagSpringJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@BeforeEach
	void setUp() {
		tagSpringJdbcRepository = new TagSpringJdbcRepository(jdbcTemplate);
	}

	@AfterEach
	void tearDown() {
		tagSpringJdbcRepository.deleteAll();
	}

	@Test
	void testSave() {
		// Given
		Tag tag = getTagForTest();

		// When
		tag = tagSpringJdbcRepository.save(tag);

		// Then
		Assertions.assertNotNull(tag.getId());
	}

	private Tag getTagForTest(){
		String tagName = "tag1";
		return new Tag(tagName);
	}
}