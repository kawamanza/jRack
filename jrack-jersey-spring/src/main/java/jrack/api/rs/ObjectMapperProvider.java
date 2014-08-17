package jrack.api.rs;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	final ObjectMapper defaultObjectMapper;

	public ObjectMapperProvider() {
		defaultObjectMapper = getObjectMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return defaultObjectMapper;
	}

	static public ObjectMapper getObjectMapper() {
		return new ObjectMapper()
				.configure(
						SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
						false /* force ISO8601 */)
				.configure(
						SerializationFeature.WRITE_ENUMS_USING_TO_STRING,
						true)
				.configure(
						DeserializationFeature.READ_ENUMS_USING_TO_STRING,
						true)
				.setSerializationInclusion(JsonInclude.Include.ALWAYS);
	}

}
