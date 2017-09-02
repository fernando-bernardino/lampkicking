package com.yoti.test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SimulationOutputTest {

	Coordinates coordinates = new Coordinates(1, 2);
	int patches = 3;

	@Test
	public void SimulateInput_coordinatesAreSet(){
		
		SimulationOutput output = new SimulationOutput(coordinates, patches);
		
		assertEquals(coordinates, output.getCoords());
	}

	@Test
	public void SimulateInput_nullCoordinates_nullIsSet(){
		
		SimulationOutput output = new SimulationOutput(null, patches);
		
		assertNull(output.getCoords());
	}

	@Test
	public void SimulateInput_patchesSet(){
		
		SimulationOutput output = new SimulationOutput(coordinates, patches);
		
		assertEquals(patches, output.getPatches());
	}
}
