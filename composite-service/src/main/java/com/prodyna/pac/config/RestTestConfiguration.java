package com.prodyna.pac.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.prodyna.pac.service.persistence.SurveyPersistenceService;

@Profile("test")
@Configuration
public class RestTestConfiguration {

	@Bean
    @Primary
    public SurveyPersistenceService SurveyPersistenceService() {
        return Mockito.mock(SurveyPersistenceService.class);
    }
}
