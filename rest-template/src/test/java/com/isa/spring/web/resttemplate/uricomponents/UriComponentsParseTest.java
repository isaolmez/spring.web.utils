package com.isa.spring.web.resttemplate.uricomponents;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class UriComponentsParseTest {

    @Test
    public void shouldParseFromUrl() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://www.test.com/path")
                .build();

        assertThat(uriComponents.getScheme()).isEqualTo("http");
        assertThat(uriComponents.getHost()).isEqualTo("www.test.com");
        assertThat(uriComponents.getPath()).isEqualTo("/path");
        assertThat(uriComponents.getQuery()).isEqualTo(null);
        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com/path");
    }

    @Test
    public void shouldConstructFromHttpUrl() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://www.test.com/path")
                .build();

        assertThat(uriComponents.getScheme()).isEqualTo("http");
        assertThat(uriComponents.getHost()).isEqualTo("www.test.com");
        assertThat(uriComponents.getPath()).isEqualTo("/path");
        assertThat(uriComponents.getQuery()).isEqualTo(null);
        assertThat(uriComponents.toUriString()).isEqualTo("http://www.test.com/path");
    }

    @Test
    public void shouldConstructFromJdbcUrl() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("jdbc:mysql://localhost/mydb")
                .build();

        assertThat(uriComponents.getScheme()).isEqualTo("jdbc");
        assertThat(uriComponents.getSchemeSpecificPart()).isEqualTo("mysql://localhost/mydb");
        assertThat(uriComponents.getHost()).isEqualTo(null);
        assertThat(uriComponents.getPath()).isEqualTo(null);
        assertThat(uriComponents.getQuery()).isEqualTo(null);
        assertThat(uriComponents.toUriString()).isEqualTo("jdbc:mysql://localhost/mydb");
    }

    @Test
    public void shouldConstructFromPath() {
        UriComponents uriComponents = UriComponentsBuilder.fromPath("/path")
                .build();

        assertThat(uriComponents.getScheme()).isEqualTo(null);
        assertThat(uriComponents.getHost()).isEqualTo(null);
        assertThat(uriComponents.getPath()).isEqualTo("/path");
        assertThat(uriComponents.getQuery()).isEqualTo(null);
        assertThat(uriComponents.toUriString()).isEqualTo("/path");
    }
}
