package com.company;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author Jeff Risberg
 * @since 11/30/17
 */
@Slf4j
public class Main {

  public static void main(String[] args) throws Exception {

    Server server = new Server(8080);

    ServletContextHandler sch = new ServletContextHandler(server, "/");
    ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
    jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, MainApplication.class.getCanonicalName());
    sch.addServlet(jerseyServletHolder, "/*");

    server.start();
    server.join();
  }
}
