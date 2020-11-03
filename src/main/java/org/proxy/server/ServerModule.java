package org.proxy.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    HttpServer server(@Named("serverVertx") Vertx vertx, HttpServerOptions options, HttpClient client) {
        return vertx.createHttpServer(options)
                .requestHandler(new VertxServerHandler(client));
    }

    @Provides
    @Singleton
    HttpServerOptions serverOptions() {
        return new HttpServerOptions()
                .setPort(8081)
                .setUsePooledBuffers(true);
    }

    @Provides
    @Singleton
    @Named("serverVertx")
    Vertx serverVertx() {
        return Vertx.vertx();
    }

}
