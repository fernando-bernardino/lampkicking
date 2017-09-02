package com.yoti.test.simulate;

import org.springframework.stereotype.Service;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;

@Service
public class SimulationInputValidator {

	/**
	 * Validates if the room size if bigger than 0 in both X, Y axis and if the initial
	 * position is inside that same room.
	 * 
	 * @param input Simulation input
	 * 
	 * @throws IllegalArgumentException If room size or initial coordinates are not valid 
	 */
	public void validate(SimulationInput input) {
		
		Coordinates roomSize = input.getRoomSize();
		
		validateRoomSize(roomSize);
		
		validateLocation(input, roomSize);
	}

	private void validateRoomSize(Coordinates roomSize) {
		
		if(roomSize.getX() < 1 || 
				roomSize.getY() < 1) {
			
			throw new IllegalArgumentException("The room should a valid size");
		}
	}

	private void validateLocation(SimulationInput input, Coordinates roomSize) {
		
		Coordinates initialLocation = input.getCoords();
		
		if (initialLocation.getX() >= roomSize.getX() || initialLocation.getX() < 0 ||
			
			initialLocation.getY() >= roomSize.getY() ||initialLocation.getY() < 0) {
			
			throw new IllegalArgumentException("Initial location cannot be outside of room");
		}
	}

}
