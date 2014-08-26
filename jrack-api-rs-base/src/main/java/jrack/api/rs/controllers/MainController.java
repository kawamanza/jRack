package jrack.api.rs.controllers;

import static java.lang.String.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
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
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("")
public class MainController {

	@Context
	private UriInfo uriInfo;

	@Context
	private ServletContext context;

	private Properties pomProps;

	@GET
	public Response root() {
		ResponseBuilder response = Response.status(301);
		response.header("Location",
				format("%sentrypoint", uriInfo.getBaseUri().toString()));
		return response.build();
	}

	@GET
	@Path("entrypoint")
	@Produces(MediaType.APPLICATION_JSON)
	public Response entrypoint() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jrack/api/rs/views/entrypoint.json");
		JsonNode entity = new ObjectMapper().readTree(in);
		ResponseBuilder response = Response.ok(entity);
		return response.build();
	}

	@GET
	@Path("status")
	@Produces(MediaType.APPLICATION_JSON)
	public Response status() throws IOException {
		if (pomProps == null) {
			pomProps = loadProps();
		}
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("jrack/api/rs/views/status.json");
		JsonNode entity = new ObjectMapper().readTree(in);
		((ObjectNode) entity.get("status")).put("version",
				pomProps.getProperty("version"));
		ResponseBuilder response = Response.ok(entity);
		return response.build();
	}

	protected Properties loadProps() {
		Properties props = new Properties();
		try {
			props.load(context
					.getResourceAsStream("/META-INF/maven/jrack/jrack-api/pom.properties"));
		} catch (IOException e) {
			props.setProperty("version", "Error loading version: " + e);
		}
		return props;
	}

}
