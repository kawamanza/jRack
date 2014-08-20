package jrack.api.rs.controllers;

import static java.lang.String.*;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("")
public class MainController {

	@Context
	private UriInfo uriInfo;

	@GET
	public Response root() {
		ResponseBuilder response = Response.status(301);
		response.header("Location", format("%sentrypoint", uriInfo.getBaseUri().toString()));
		return response.build();
	}

	@GET
	@Path("entrypoint")
	@Produces(MediaType.APPLICATION_JSON)
	public Response entrypoint() throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("jrack/api/rs/views/entrypoint.json");
		JsonNode entity = new ObjectMapper().readTree(in);
		ResponseBuilder response = Response.ok(entity);
		return response.build();
	}

}
