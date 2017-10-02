package com.upside.echo.v1.client;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Basic/default request retry handler for our JaxrsClients</p>
 */
public class UpsideRequestRetryHandler implements HttpRequestRetryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int DEFAULT_MAX_RETRY = 5;
    private static final String DEFAULT_CLIENT_NAME = "";
    private final String clientName;
    private final int maxRetry;

    public UpsideRequestRetryHandler() {
        this(DEFAULT_MAX_RETRY, DEFAULT_CLIENT_NAME);
    }

    public UpsideRequestRetryHandler(int maxRetry, String clientName) {
        this.maxRetry = maxRetry;
        this.clientName = clientName;

        LOGGER.info("Constructing new UpsideRequestRetryHandler for clientName '{}' with maxRetries '{}'",
                    this.clientName, this.maxRetry);
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
        LOGGER.warn("[{}] encountered exception making Jaxrs request; retry attempt '{}'. Exception: '{}'",
                    this.clientName, executionCount, exception.getMessage());
        if (executionCount >= this.maxRetry) {
            return false;
        }
        if (exception instanceof UnknownHostException) {
            return false;
        }
        if (exception instanceof SSLException) {
            return false;
        }

        return true;

//        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
//        HttpRequest request = clientContext.getRequest();
//        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
//        if (idempotent) {
//            // Retry if the request is considered idempotent
//            return true;
//        }
//        return false;
    }

}
