package org.proxy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.http.HttpServer;
import org.proxy.client.ClientModule;
import org.proxy.server.ServerModule;

public class Main {

    public static void main(String[] args) {
        System.out.println("Before");
        Injector injector = Guice.createInjector(new ClientModule(), new ServerModule());
        injector.getInstance(HttpServer.class).listen();
        System.out.println("Started");
    }
}
