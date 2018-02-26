package com.isa.spring.web.resttemplate.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PathAwareInterceptor implements ClientHttpRequestInterceptor {

    private final List<String> includePatterns = new ArrayList<>();

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final ClientHttpRequestInterceptor interceptor;

    public PathAwareInterceptor(List<String> includePatterns, ClientHttpRequestInterceptor interceptor) {
        this.includePatterns.addAll(includePatterns);
        this.interceptor = interceptor;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        UriComponents uriComponents = UriComponentsBuilder.fromUri(request.getURI()).build();
        String requestPath = uriComponents.getPath();
        boolean matches = includePatterns.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestPath));
        if (matches) {
            return interceptor.intercept(request, body, execution);
        }

        return execution.execute(request, body);
    }
}
