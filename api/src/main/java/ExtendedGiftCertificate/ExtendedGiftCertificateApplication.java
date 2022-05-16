package ExtendedGiftCertificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages =
		{
				"com.itechart.esm.common",
				"com.itechart.esm.repository",
				"com.itechart.esm.service",
				"com.itechart.esm.controller"				
		})
public class ExtendedGiftCertificateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtendedGiftCertificateApplication.class, args);
	}

}
