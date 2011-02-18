package org.exemplar.resources;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ws.rs.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Path("/delphi/surveys/{id}")
public class SurveyResource {
    @DELETE
    @Produces("text/plain")
    public String deleteSurvey(
        @PathParam("id") String id
    ) throws IOException, TemplateException
    {
        Survey.delete(id);
        return "OK";
    }

    @POST
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

}


