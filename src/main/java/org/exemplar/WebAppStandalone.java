package org.exemplar;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import java.io.File;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebAppStandalone {
    public static Configuration cfg;

    public static void main(String[] args) throws IOException {
        final String baseUri = "http://localhost:9998/";
        final Map<String, String> initParams = new HashMap<String, String>();

        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("static/templates"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        initParams.put("com.sun.jersey.config.property.packages", "org.exemplar.resources");

        System.out.println("Starting grizzly...");
        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
        System.out.println(String.format("Try out %sdelphi/login\nHit enter to stop it...", baseUri));
        System.in.read();
        threadSelector.stopEndpoint();
        System.exit(0);
    }
}
