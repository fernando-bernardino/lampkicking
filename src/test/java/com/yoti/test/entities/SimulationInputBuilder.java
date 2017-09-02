package com.yoti.test.entities;

import java.util.List;

public class SimulationInputBuilder {

	public static SimulationInput createEmptySimulationInput() {
		return createSimulationInput(null, null);
	}
	
	public static SimulationInput createSimulationInput(
			Coordinates roomSize, Coordinates coordinates) {
		
		return createSimulationInput(roomSize, coordinates, null, null);
	}
	
	public static SimulationInput createSimulationInput(
			Coordinates roomSize, Coordinates coordinates, String directions) {
		
		return createSimulationInput(roomSize, coordinates, null, directions);
	}
	
	public static SimulationInput createSimulationInput(
			Coordinates roomSize, Coordinates coordinates, List<Coordinates> patches, String directions) {
		
		return new SimulationInput(roomSize, coordinates, patches, directions);
	}
}
