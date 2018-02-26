package com.isa.spring.web.resttemplate.uricomponents;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponentsConstructTest {

    @Test
    public void shouldConstruct() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .build();

        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com/path");
    }

    @Test
    public void shouldConstructWithPort() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .port("8080")
                .build();

        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com:8080/path");
    }

    @Test
    public void shouldConstructWithQueryParameter() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .queryParam("key1", "value1")
                .queryParam("key2", "value2")
                .build();

        assertThat(uriComponents.toUriString())
                .isEqualTo("http://www.test.com/path?key1=value1&key2=value2");
    }

    @Test
    public void shouldConstructAndEncode() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .queryParam("key", "with space")
                .build()
                .encode();

        assertThat(uriComponents.toUriString())
                .isEqualTo("http://www.test.com/path?key=with%20space");
    }

    @Test
    public void shouldNotEncodeByDefault() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .queryParam("key", "with space")
                .build();

        assertThat(uriComponents.toUriString())
                .isEqualTo("http://www.test.com/path?key=with space");
    }

    @Test
    public void shouldConstructAndExpandForPath() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path_{suffix}")
                .buildAndExpand("expanded");

        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com/path_expanded");
    }

    @Test
    public void shouldConstructAndExpandForQueryString() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.test.com")
                .path("/path")
                .queryParam("key", "{value}")
                .buildAndExpand("expanded");

        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com/path?key=expanded");
    }
}
