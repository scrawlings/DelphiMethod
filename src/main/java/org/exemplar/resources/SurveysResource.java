package org.exemplar.resources;

import org.exemplar.resources.Survey;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Path("/delphi/surveys")
public class SurveysResource {

    @DELETE
    @Path("{id}")
    @Produces("text/plain")
    public String deleteSurvey(
        @PathParam("id") String id
    ) throws IOException, TemplateException
    {
        Survey.delete(id);
        return "OK";
    }

    @POST
    @Path("{id}")
    @Produces("text/plain")
    public String deleteSurveyForPrototype(
        @PathParam("id") String id,
        @FormParam("_method") String method
    ) throws IOException, TemplateException
    {
        if ("delete".equals(method)) {
            return deleteSurvey(id);
        }
        return "OK";
    }

    @GET
    @Produces("text/html")
    public StreamingOutput presentVisibleSurveys() throws IOException
    {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                Map root = new HashMap();
                root.put("surveys", Survey.all());
                Template page = org.exemplar.WebAppStandalone.cfg.getTemplate("surveys.ftl");
                try {
                    page.process(root, new OutputStreamWriter(output));
                } catch (TemplateException te) {
                    throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
                }
            }
        };
    }

    @POST
    @Produces("text/html")
    public StreamingOutput createNewSurvey(
        @FormParam("title") String title,
        @FormParam("description") String description
    ) throws IOException
    {
        new Survey(title, description).persist();
        return presentVisibleSurveys();
    }

}


