package org.exemplar.resources;

import org.exemplar.resources.Survey;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.*;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Path("/delphi/surveys")
public class SurveysResource {

    @GET
    @Produces("text/html")
    public String presentVisibleSurveys() throws IOException, TemplateException
    {
        StringWriter renderedPage = new StringWriter();
        Map root = new HashMap();
        Template page = org.exemplar.WebAppStandalone.cfg.getTemplate("surveys.ftl");

        root.put("surveys", Survey.all());

        page.process(root, renderedPage);

        return renderedPage.toString();
    }

    @POST
    @Produces("text/html")
    public String createNewSurvey(
        @FormParam("title") String title,
        @FormParam("description") String description
    ) throws IOException, TemplateException
    {
        new Survey(title, description).persist();
        return presentVisibleSurveys();
    }

}


