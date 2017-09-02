package com.yoti.test.simulate;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;

@Service
public class PathCalculator {

	/**
	 * Generates the path described using an initial position and a set of instructions.
	 * Given a initial position ({@link SimulationInput#getCoords()}) it will use the instructions
	 * ({@link SimulationInput#getInstructions()}) to generate the path described.<br/>
	 * 
	 * In case of an instruction describes a move against the limits (upper and lower) of the room, the
	 * instruction will be ignored. The initial position will NOT be included in the path.
	 * 
	 * @param input Input for the simulation {@link SimulationInput}}
	 * @param pathAccumulator Collection where the consecutive positions are accumulated
	 * 
	 * @return the final position where the simulation stopped
	 * 
	 * @throws IllegalArgumentException If any instruction is not within the values {N, S, W, E}
	 * 
	 */
	public Coordinates calculatePath(SimulationInput input, Collection<Coordinates> pathAccumulator) {
		Coordinates currentPosition = input.getCoords();
		String instructions = input.getInstructions();
		
		for (char c : instructions.toCharArray()) {
			Coordinates newPosition = calculateNewPosition(currentPosition, c, input.getRoomSize());
			
			if (newPosition != null) {
				
				pathAccumulator.add(newPosition);
				currentPosition = newPosition;
			}
		}
		
		return currentPosition;
	}

	private Coordinates calculateNewPosition(Coordinates position, char c, Coordinates roomSize) {
		
		if(c == 'N') {
			return goNorth(position, roomSize);
			
		} else if (c == 'S') {
			return goSouth(position);
			
		} else if (c == 'E'){
			return goEast(position, roomSize);
			
		} else if (c == 'W'){
			return goWest(position);
			
		} else {
			throw new IllegalArgumentException("Invalid instruction " + c + ", must be one of {N,S,E,W}");
		}
	}

	private Coordinates goNorth(Coordinates position, Coordinates roomSize) {
		
		int newY = position.getY() + 1;
		
		return newY < roomSize.getY() ?
					new Coordinates(position.getX(), newY):
					null;
	}

	private Coordinates goSouth(Coordinates position) {
		
		int newY = position.getY() - 1;
		
		return newY < 0 ?
					null :
					new Coordinates(position.getX(), newY);
	}

	private Coordinates goEast(Coordinates position, Coordinates roomSize) {
		
		int newX = position.getX() + 1;
		
		return newX < roomSize.getX() ?
				new Coordinates(newX, position.getY()) :
				null;
	}

	private Coordinates goWest(Coordinates position) {
		
		int newX = position.getX() - 1;
		
		return newX < 0 ?
					null :
					new Coordinates(newX, position.getY());
	}
}
