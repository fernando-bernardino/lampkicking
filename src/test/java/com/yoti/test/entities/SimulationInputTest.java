package com.yoti.test.entities;

import static com.yoti.test.entities.SimulationInputBuilder.createSimulationInput;
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
	public void SimulateInput_initialCoordinatesAreSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates);
		
		assertEquals(coordinates, input.getCoords());
	}

	@Test
	public void SimulateInput_initialRoomSizeSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates);
		
		assertEquals(roomSize, input.getRoomSize());
	}

	@Test
	public void SimulateInput_instructionsSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, instructions);
		
		assertEquals(instructions, input.getInstructions());
	}

	@Test
	public void SimulateInput_nullInstructions_emptyValueSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, null);
		
		assertEquals(0, input.getInstructions().length());
	}

	@Test
	public void SimulateInput_patchesAreSet(){
		Coordinates location = new Coordinates(1, 2);
		patches.add(location);
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, patches, instructions);
		
		assertEquals(patches, input.getPatches());
	}

	@Test
	public void SimulateInput_nullPatches_emptyListIsSet(){
		
		SimulationInput input = createSimulationInput(roomSize, coordinates, null, instructions);
		
		assertEquals(0, input.getPatches().size());
	}
}
