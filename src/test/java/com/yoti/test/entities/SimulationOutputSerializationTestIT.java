package com.yoti.test.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimulationOutputSerializationTestIT {

	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void createSimulationInputFromJson() throws Exception {
		
		SimulationOutput output = new SimulationOutput(new Coordinates(1, 3), 1);
		
		JsonNode root = mapper.readTree(mapper.writeValueAsBytes(output));
		
		JsonNode coords = root.get("coords");
		assertEquals(1, coords.get(0).asInt());
		assertEquals(3, coords.get(1).asInt());
		
		assertEquals(1, root.get("patches").asInt());
	}
}
