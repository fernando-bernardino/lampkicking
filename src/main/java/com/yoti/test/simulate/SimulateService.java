package com.yoti.test.simulate;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

@Service
public class SimulateService {
	
	@Autowired
	SimulationInputValidator simulationInputValidator;
	
	@Autowired
	PathCalculator pathCalculator;
	
	@Autowired
	CollectionUnion collectionUnion;

	/**
	 * Runs a simulation using the input provided.<br/>
	 * 
	 * The coordinates of initial location and size of the room needs to be integers
	 * greater than zero. The coordinates for initial location cannot be outside the 
	 * room.<br/>
	 * 
	 * <b>Example:</b> If the room is 5 x 5, valid location positions inside of the room
	 * will go from 0 to 4 in X and Y coordinates.<br/>
	 * 
	 * Empty instructions are considered as no instructions -> initial locations
	 * is returned as final location; number of patches will be the number of passed patches.<br/>
	 * 
	 * Empty patches will be considered as no locations is dirty.
	 * 
	 * @param input Input information for the simulation (see {@link SimulationInput})
	 * 
	 * @return final result (see {@link SimulationInput})
	 * 
	 * @throws IllegalArgumentException If the input provided is not valid
	 * 
	 */
	public SimulationOutput simulate(SimulationInput input) {

		simulationInputValidator.validate(input);
		
		HashSet<Coordinates> pathAccumulator = new HashSet<>();
		
		Coordinates finalPosition = pathCalculator.calculatePath(input, pathAccumulator);
		
		int numberCleanedPatches = collectionUnion.countMatches(pathAccumulator, input.getPatches());
		
		return new SimulationOutput(finalPosition, numberCleanedPatches);
	}
}
