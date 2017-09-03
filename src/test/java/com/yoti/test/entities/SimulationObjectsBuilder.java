package com.yoti.test.entities;

import java.util.ArrayList;
import java.util.List;

public class SimulationObjectsBuilder {

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
	
	public static SimulationInput createMeaninfulSimulationInput() {
			
		List<Coordinates> patches = new ArrayList<>();
		patches.add(new Coordinates(1,  1));
		patches.add(new Coordinates(2,  2));
		
		SimulationInput input = new SimulationInput(
				new Coordinates(5, 5),
				new Coordinates(1,2),
				patches,
				"NESW");
		
		return input;
	}
	
	public static SimulationOutput createMeaninfulSimulationOutput() {
		return new SimulationOutput(new Coordinates(1, 2), 1);
	}
	
	public static SimulationResult createMeaninfulSimulationResult () {
		return new SimulationResult(
				createMeaninfulSimulationInput(), 
				createMeaninfulSimulationOutput());
	}
	
	public static ErrorMessage createMeaninfulError() {
		return new ErrorMessage(400, "Invalid room size");
	}
	
	public static SimulationError createMeaninfulSimulationError() {
		return new SimulationError(
				createMeaninfulSimulationInput(),
				createMeaninfulError());
	}
}
