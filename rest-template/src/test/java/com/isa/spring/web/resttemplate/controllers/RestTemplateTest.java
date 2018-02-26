package com.isa.spring.web.resttemplate.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTest {

    private static final String DOMAIN = "http://localhost:9999/api/";

    @Test
    public void shouldGet() {
        ResponseEntity<Void> responseEntity = getRestTemplate()
                .getForEntity(DOMAIN + "get", Void.class);

        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldThrowExceptionForBadRequest() {
        ResponseEntity<Void> responseEntity = getRestTemplate()
                .getForEntity(DOMAIN + "get400", Void.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldThrowExceptionForNotFound() {
        getRestTemplate().getForEntity(DOMAIN + "get404", Void.class);

        fail("Should have failed!");
    }

    @Test(expected = ResourceAccessException.class)
    public void shouldThrowExceptionForTimeout() {
        getRestTemplate(1000).getForEntity(DOMAIN + "getTimeout", Void.class);

        fail("Should have failed!");
    }

    @Test
    public void shouldNotThrowExceptionForTimeout() {
        getRestTemplate(1500).getForEntity(DOMAIN + "getTimeout", Void.class);
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(new BufferingClientHttpRequestFactory(
                        new HttpComponentsClientHttpRequestFactory()))
                .build();
    }

    private RestTemplate getRestTemplate(int milliseconds) {
        return new RestTemplateBuilder()
                .requestFactory(new BufferingClientHttpRequestFactory(
                        new HttpComponentsClientHttpRequestFactory()))
                .setReadTimeout(milliseconds)
                .build();
    }
}
