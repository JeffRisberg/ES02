package com.company;

import com.google.inject.Guice;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

/**
 * @author Jeff Risberg
 * @since 11/30/17
 */
public class MainApplication extends ResourceConfig {
    @Inject
    public MainApplication(ServiceLocator serviceLocator) {

        packages(MainApplication.class.getPackage().getName());
        register(JacksonFeature.class);

        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        //guiceBridge.bridgeGuiceInjector(Guice.createInjector(new MainModule()));
    }
}