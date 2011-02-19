package org.exemplar.resources;

import org.exemplar.resources.Survey;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.URI;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

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
    @Path("{id}")
    @Produces("text/html")
    public StreamingOutput showSingleSurvey(
        final @PathParam("id") String id
    ) {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                Map root = new HashMap();
                root.put("survey", Survey.recover(id));
                Template page = org.exemplar.WebAppStandalone.cfg.getTemplate("survey.ftl");
                try {
                    page.process(root, new OutputStreamWriter(output));
                } catch (TemplateException te) {
                    throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
                }
            }
        };
    }

    @POST
    @Path("{id}")
    @Produces("text/html")
    public StreamingOutput addOptionsToSurvey(
        @PathParam("id") String id,
        MultivaluedMap<String, String> formParams
    )
    {
        Survey survey = Survey.recover(id);

        survey.resetOptions();
        if (formParams.get("option") != null) {
            for (String option : formParams.get("option")) {
                survey.appendOption(option);
            }
        }

        survey.persist();
        return showSingleSurvey(id);
    }

    @GET
    @Produces("text/html")
    public StreamingOutput presentVisibleSurveys()
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
    )
    {
        new Survey(title, description).persist();
        return presentVisibleSurveys();
    }


}


