package jrack.api.rs.controllers;

import static com.jayway.jsonassert.JsonAssert.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import jrack.test.AbstractControllerTestCase;

import org.junit.Test;

public class MainControllerTest extends AbstractControllerTestCase<MainController> {

	@Test
	public void testEntrypoint() throws IOException {
		Response response = target("/entrypoint").request().get();
		assertEquals(200, response.getStatus());
		assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

		with((InputStream) response.getEntity()).assertEquals(
				"$._links.self.href", "/entrypoint");
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

		with((InputStream) response.getEntity()).assertEquals(
				"$.status.version", "1.0.beta");
	}

}
