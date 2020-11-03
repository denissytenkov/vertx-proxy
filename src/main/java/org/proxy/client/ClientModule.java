package org.proxy.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    @Named("clientVertx")
    Vertx clientVertx() {
        VertxOptions options = new VertxOptions();
        return Vertx.vertx();
    }

    @Provides
    @Singleton
    HttpClient client(@Named("clientVertx") Vertx vertx, HttpClientOptions clientOptions) {
        return vertx.createHttpClient(clientOptions);
    }

    @Provides
    @Singleton
    HttpClientOptions clientOptions() {
        return new HttpClientOptions()
                .setSsl(true)
                .setTrustAll(true)
                .setPipelining(true)
                .setUsePooledBuffers(true);
    }

}
