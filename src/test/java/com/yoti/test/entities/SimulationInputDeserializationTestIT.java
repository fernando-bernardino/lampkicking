package com.yoti.test.entities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimulationInputDeserializationTestIT {
	
	public static final int X = 0;
	public static final int Y = 1;

	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void createSimulationInputFromJson() throws Exception {
		
		String json = "{" +
				"\"roomSize\" : [5, 5]," +
				"\"coords\" : [1, 2]," +
				"\"patches\" : [[1, 0], [2, 2],[2, 3]]," +
				"\"instructions\" : \"NNESEESWNWW\"" +
			"}";
		
		SimulationInput input = mapper.readValue(json, SimulationInput.class);
		
		assertEquals("Room size was not properly deserialized", new Coordinates(5, 5), input.getRoomSize());
		assertEquals("Coords were not properly deserialized", new Coordinates(1, 2), input.getCoords());
		assertEquals("NNESEESWNWW", input.getInstructions());
		assertPatchesMatch(new int[] []  {{1, 0}, {2, 2}, {2, 3}}, input.getPatches());
	}

	private void assertPatchesMatch(int [][] expected, List<Coordinates> returned) {
		
		assertEquals(expected.length, returned.size());
		
		List<Coordinates> expectedAsCoordinates = Arrays.stream(
						expected).map(
										(c) -> new Coordinates(c[X], c[Y]))
								.collect(
										Collectors.toList());
		
		assertEquals(expectedAsCoordinates, returned);
	}
}
