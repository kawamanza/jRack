package jrack.api.rs.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MainControllerTest extends AbstractControllerTestCase<MainController> {

	@Test
	public void testEntrypoint() {
		Response response = target("/entrypoint").request().get();
		assertEquals(200, response.getStatus());
		assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
		assertNotNull(response.getEntity());
	}

	@Test
	public void testStatus() throws IOException {
		Properties props = new Properties();
		props.setProperty("version", "1.0.beta");
		doReturn(props).when(controller).loadProps();

		Builder request = target("/status").request();
		Response response = request.get();
		assertEquals(200, response.getStatus());
		assertEquals("application/json",
				response.getHeaders().getFirst("Content-Type"));

		InputStream entity = (InputStream) response.getEntity();
		assertNotNull(response.getEntity());

		JsonNode body = new ObjectMapper().readTree(entity);
		assertFalse(body.path("status").isMissingNode());
		assertEquals("1.0.beta", ((ObjectNode) body.get("status")).get("version")
				.asText());
	}

}
