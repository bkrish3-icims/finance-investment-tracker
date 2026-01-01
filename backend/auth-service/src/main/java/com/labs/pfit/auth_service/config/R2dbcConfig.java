package com.labs.pfit.auth_service.config;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class R2dbcConfig {

	@Bean
	public ConversionService r2dbcConversionService() {
		DefaultConversionService conversionService = new DefaultConversionService();
		conversionService.addConverter(OffsetDateTime.class, Timestamp.class,
			odt -> Timestamp.from(odt.toInstant()));

		conversionService.addConverter(Timestamp.class, OffsetDateTime.class,
			ts -> ts.toInstant().atOffset(ZoneOffset.UTC));

		return conversionService;
	}
}
