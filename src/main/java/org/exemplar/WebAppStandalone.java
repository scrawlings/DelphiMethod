package org.exemplar;


import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import java.io.File;

import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import com.sun.grizzly.tcp.http11.GrizzlyAdapter;
import com.sun.grizzly.tcp.http11.GrizzlyRequest;
import com.sun.grizzly.tcp.http11.GrizzlyResponse;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import java.io.IOException;

public class WebAppStandalone {
    public static Configuration cfg;
    
    public static void main(String[] args) throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("static/templates"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        
        ServletAdapter jerseyAdapter = new ServletAdapter();
        jerseyAdapter.addInitParameter("com.sun.jersey.config.property.packages", "org.exemplar.resources");
        jerseyAdapter.setContextPath("/delphi");
        jerseyAdapter.setServletInstance(new ServletContainer());

        GrizzlyWebServer gws = new GrizzlyWebServer(9998);
        gws.addGrizzlyAdapter(jerseyAdapter, new String[] {"/delphi"});
        gws.addGrizzlyAdapter(new StaticGrizzlyAdapter("static/web"), new String[] {"/"});
        
        gws.start();
    }
    
    static class StaticGrizzlyAdapter extends GrizzlyAdapter { 
        public StaticGrizzlyAdapter(String publicDirectory) { 
    	    super(publicDirectory);
            setHandleStaticResources(true);
        } 

        public void service(GrizzlyRequest grizzlyRequest, GrizzlyResponse grizzlyResponse) {
        	try { 
                grizzlyResponse.setStatus(404); 
                grizzlyResponse.getWriter().print("Resoure can not be found");
            } catch (IOException e) { }
        } 
    } 
}
