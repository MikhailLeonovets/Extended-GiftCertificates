package com.itechart.esm.repository.jdbc_template;

import com.itechart.esm.repository.entity.GiftCertificate;
import com.itechart.esm.repository.entity.builder.GiftCertificateBuilder;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestSpringJdbcConfiguration.class)
class GiftCertificateSpringJdbcRepositoryTest {
	private final JdbcTemplate jdbcTemplate;

	private GiftCertificateSpringJdbcRepository giftCertificateSpringJdbcRepository;

	@Autowired
	GiftCertificateSpringJdbcRepositoryTest(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@BeforeEach
	void setUp() {
		giftCertificateSpringJdbcRepository = new GiftCertificateSpringJdbcRepository(jdbcTemplate);
	}

	@AfterEach
	void tearDown() {
		giftCertificateSpringJdbcRepository.deleteAll();
	}

	@Test
	void testSave() {
		// Given
		GiftCertificate giftCertificate = getGiftCertificateForTest();

		// When
		giftCertificate = giftCertificateSpringJdbcRepository.save(giftCertificate);

		// Then
		Assertions.assertFalse(giftCertificateSpringJdbcRepository.findByPartOfName(giftCertificate.getName())
				.isEmpty());
	}

	@Test
	void testFindByPartOfName() {
		// Given
		GiftCertificate giftCertificate = getGiftCertificateForTest();
		giftCertificateSpringJdbcRepository.save(giftCertificate);
		String partOfName = giftCertificate.getName().substring(2);

		// When
		Optional<GiftCertificate> giftCertificate1 =
				giftCertificateSpringJdbcRepository.findByPartOfName(partOfName).stream().findFirst();

		// Then
		Assertions.assertTrue(giftCertificate1.isPresent());
	}

	@Test
	void findByPartOfDescription() {
		// Given
		GiftCertificate giftCertificate = getGiftCertificateForTest();
		giftCertificateSpringJdbcRepository.save(giftCertificate);
		String partOfDescription = giftCertificate.getDescription().substring(2);

		// When
		Optional<GiftCertificate> giftCertificate1 =
				giftCertificateSpringJdbcRepository.findByPartOfDescription(partOfDescription).stream().findFirst();

		// Then
		Assertions.assertTrue(giftCertificate1.isPresent());
	}

	private GiftCertificate getGiftCertificateForTest() {
		String name = "gift1";
		String description = "desc1";
		BigDecimal price = BigDecimal.TEN;
		Period period = Period.of(10, 10, 5);
		return new GiftCertificateBuilder()
				.setName(name)
				.setDescription(description)
				.setPrice(price)
				.setDateOfCreation(LocalDateTime.now())
				.setExpirationPeriod(period)
				.build();
	}
}