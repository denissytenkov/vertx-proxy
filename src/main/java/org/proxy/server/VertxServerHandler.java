package org.proxy.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.vertx.core.Handler;
import io.vertx.core.http.*;

@Singleton
public class VertxServerHandler implements Handler<HttpServerRequest> {
    private final HttpClient client;

    @Inject
    public VertxServerHandler(HttpClient client) {
        this.client = client;
    }

    @Override
    public void handle(HttpServerRequest serverRequest) {
        HttpServerResponse serverResponse = serverRequest.response();
        String destination = serverRequest.getHeader("x-destination");
        RequestOptions requestOptions = new RequestOptions()
                .setHost(destination)
                .setPort(443)
                .setSsl(true)
                .setURI(serverRequest.uri())
                .addHeader("Host", destination);

        client.request(HttpMethod.GET, requestOptions, response -> {
            response.headers().entries()
                    .forEach(e -> {
                        serverResponse.headers().add(e.getKey(), e.getValue());
                    });

            serverResponse.setStatusCode(response.statusCode());
            response.handler(serverResponse::write);
            response.endHandler(end -> serverResponse.end());
            response.exceptionHandler(e -> handleError(serverResponse, e));
        }).end();

    }

    private void handleError(HttpServerResponse serverResponse, Throwable e) {
            serverResponse.setStatusCode(500);
            serverResponse.end();
    }
}
