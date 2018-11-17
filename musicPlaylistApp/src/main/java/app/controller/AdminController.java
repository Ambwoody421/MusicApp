package app.controller;

import app.model.RaspberryPi;
import app.model.Session;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminController {

    @POST
    @Path("/shutdown")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response initiateShutdown(@CookieParam("sessId") String sessionId) {

        try {
            //check if cookie was sent
            if (sessionId == null) {
                System.out.println("Sess Id empty");
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if (userId == 1) {
                RaspberryPi.shutdown();
                return Response.status(201).entity("Shutting Down").build();
            }

            return Response.status(201).entity("Something went wrong").build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }
}
