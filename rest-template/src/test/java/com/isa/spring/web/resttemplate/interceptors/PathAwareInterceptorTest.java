package com.isa.spring.web.resttemplate.interceptors;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;

@RunWith(MockitoJUnitRunner.class)
public class PathAwareInterceptorTest {

    private PathAwareInterceptor pathAwareInterceptor;

    @Mock
    private ClientHttpRequestInterceptor delegated;

    @Mock
    private HttpRequest request;

    @Mock
    private ClientHttpRequestExecution execution;

    private byte[] body = new byte[0];

    @Before
    public void setUp() {
        pathAwareInterceptor = new PathAwareInterceptor(Lists.newArrayList("/profile/**"),
                delegated);
    }

    @Test
    public void shouldMatch() throws IOException {
        final String matchingUri = "http://www.test.com/profile";
        when(request.getURI()).thenReturn(URI.create(matchingUri));

        pathAwareInterceptor.intercept(request, body, execution);

        verify(delegated).intercept(eq(request), eq(body), eq(execution));
        verifyZeroInteractions(execution);
    }

    @Test
    public void shouldNotMatch() throws IOException {
        final String nonMatchingUri = "http://www.test.com/non";
        when(request.getURI()).thenReturn(URI.create(nonMatchingUri));

        pathAwareInterceptor.intercept(request, body, execution);

        verifyZeroInteractions(delegated);
        verify(execution).execute(eq(request), eq(body));
    }
}
