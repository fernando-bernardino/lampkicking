package com.yoti.test.entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CoordinatesSerializer extends StdSerializer<Coordinates> {

	private static final long serialVersionUID = 3231879521813553145L;

	public CoordinatesSerializer() {
		super(Coordinates.class);
	}

	@Override
	public void serialize(Coordinates value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		
		gen.writeStartArray();
		gen.writeNumber(value.getX());
		gen.writeNumber(value.getY());
		gen.writeEndArray();
	}
}
