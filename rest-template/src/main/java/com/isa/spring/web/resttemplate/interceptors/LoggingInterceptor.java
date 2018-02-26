package com.isa.spring.web.resttemplate.interceptors;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    static Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        log.info("Request started for {}, with content: {}", request.getURI().toString(), String.valueOf(body));
        ClientHttpResponse clientHttpResponse = execution.execute(request, body);
        log.info("Request finished for {}, with status: {}", request.getURI().toString(),
                clientHttpResponse.getRawStatusCode());
        return clientHttpResponse;
    }
}
