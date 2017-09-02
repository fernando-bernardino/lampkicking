package com.yoti.test.entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CoordinatesDeserializer extends StdDeserializer<Coordinates> {

	private static final long serialVersionUID = 3198014571409273374L;

	public CoordinatesDeserializer() { 
		super(Coordinates.class); 
	}

	@Override
	public Coordinates deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		JsonNode node = jp.getCodec().readTree(jp);
	
		return new Coordinates(
				node.get(0).intValue(), 
				node.get(1).intValue());
	}
}
