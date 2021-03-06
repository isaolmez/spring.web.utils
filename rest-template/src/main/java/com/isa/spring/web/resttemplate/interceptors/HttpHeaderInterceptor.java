package com.isa.spring.web.resttemplate.interceptors;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class HttpHeaderInterceptor implements ClientHttpRequestInterceptor {

    private final String name;
    private final String value;

    public HttpHeaderInterceptor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(name, value);
        return execution.execute(request, body);
    }
}
