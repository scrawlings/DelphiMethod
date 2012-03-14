package org.exemplar.resources;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Path("/delphi")
public class WelcomeResource {

    @GET
    @Produces("text/html")
    public Response getClichedMessage(
        @CookieParam("delphi_user") String delphiUser,
        @QueryParam("action") String action
    ) throws IOException, TemplateException
    {
        Response.ResponseBuilder response;

        if ("logout".equals(action)) {
            response = Response
                        .temporaryRedirect(UriBuilder.fromResource(WelcomeResource.class).build())
                        .cookie(new NewCookie(new NewCookie("delphi_user", ""), "deleting cookie", 0, false));

        } else {
            StringWriter renderedPage = new StringWriter();
            Map root = new HashMap();
            Template page;
            if (delphiUser == null) {
                page = org.exemplar.WebAppStandalone.cfg.getTemplate("welcome.ftl");
            } else {
                page = org.exemplar.WebAppStandalone.cfg.getTemplate("root.ftl");
                root.put("username", delphiUser);
            }
            page.process(root, renderedPage);
            response = Response.ok(renderedPage.toString());
        }

        return response.build();

    }

    @POST
    @Produces("text/html")
    public Response logInUser(
        @FormParam("username") String username
    ) throws IOException, TemplateException
    {
        StringWriter renderedPage = new StringWriter();
        Template page = org.exemplar.WebAppStandalone.cfg.getTemplate("root.ftl");

        Map root = new HashMap();
        root.put("username", username);

        page.process(root, renderedPage);

        return Response
                .ok(renderedPage.toString())
                .cookie(new NewCookie("delphi_user", username))
                .build();

    }
}

