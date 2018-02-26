package com.isa.spring.web.resttemplate.interceptors;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Appender;
import java.io.IOException;
import java.net.URI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

@RunWith(MockitoJUnitRunner.class)
public class LoggingInterceptorTest {

    private LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

    private Logger log = (ch.qos.logback.classic.Logger) LoggingInterceptor.log;

    @Mock
    private Appender appender;

    @Mock
    private HttpRequest request;

    @Mock
    private ClientHttpRequestExecution execution;

    @Mock
    private ClientHttpResponse response;

    private byte[] body = "OK".getBytes();

    @Before
    public void setUp() throws IOException {
        when(request.getURI()).thenReturn(URI.create("http://test.com"));
        when(response.getRawStatusCode()).thenReturn(200);
        when(execution.execute(eq(request), eq(body))).thenReturn(response);
        when(appender.getName()).thenReturn("MOCK");
        log.addAppender(appender);
    }

    @Test
    public void shouldLog() throws IOException {
        loggingInterceptor.intercept(request, body, execution);

        verify(appender, times(2)).doAppend(any());
    }
}
