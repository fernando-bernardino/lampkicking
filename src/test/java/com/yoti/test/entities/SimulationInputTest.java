package com.yoti.test.entities;

import static com.yoti.test.entities.SimulationObjectsBuilder.createSimulationInput;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SimulationInputTest {

	Coordinates coordinates = new Coordinates(1, 2);
	Coordinates roomSize = new Coordinates(3, 4);
	String instructions = "directions";
	List<Coordinates> patches = new ArrayList<>();


	@Test
	public void SimulateInput_nullInstructions_emptyValueSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, null);
		
		assertEquals(0, input.getInstructions().length());
	}

	@Test
	public void SimulateInput_nullPatches_emptyListIsSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, null, instructions);
		
		assertEquals(0, input.getPatches().size());
	}
}
