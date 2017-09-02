package com.yoti.test.simulate;

import static com.yoti.test.entities.SimulationInputBuilder.createSimulationInput;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;

public class PathCalculatorTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	PathCalculator calculator = new PathCalculator();
	
	List<Coordinates> pathAccumulator = new ArrayList<>();
	
	@Test
	public void calculatePath_emptyInstructions_noPath() {
		
		runTest(new Coordinates(1, 1), "");
		
		assertEquals(0, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_emptyInstructions_finalPositionSameAsInitial() {
		
		Coordinates initialLocation = new Coordinates(1, 1);
		
		Coordinates finalLocation = runTest(initialLocation, "");
		
		assertEquals(initialLocation, finalLocation);
	}
	
	@Test
	public void calculatePath_goingNorth_pathHasPositionUp() {

		runTest(new Coordinates(0, 0), "N");
		
		assertEquals(new Coordinates(0, 1), pathAccumulator.get(0));
	}
	
	@Test
	public void calculatePath_goingSouth_pathHasPositionDown() {

		runTest(new Coordinates(1, 1), "S");
		
		assertEquals(new Coordinates(1, 0), pathAccumulator.get(0));
	}
	
	@Test
	public void calculatePath_goingEast_pathHasPositionToRight() {

		runTest(new Coordinates(0, 0), "E");
		
		assertEquals(new Coordinates(1, 0), pathAccumulator.get(0));
	}
	
	@Test
	public void calculatePath_goingWest_pathHasPositiontoLeft() {

		runTest(new Coordinates(1, 1), "W");
		
		assertEquals(new Coordinates(0, 1), pathAccumulator.get(0));
	}
	
	@Test
	public void calculatePath_invalidInstruction_throwsException() {

		exception.expect(IllegalArgumentException.class);

		runTest(new Coordinates(1, 1), "A");
	}
	
	@Test
	public void calculatePath_goingNorthAgainstTheWall_noPathIsProduced() {

		runTest(new Coordinates(0, 1), "N");
		
		assertEquals(0, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_goingSouthAgainstTheWall_noPathIsProduced() {

		runTest(new Coordinates(0, 0), "S");
		
		assertEquals(0, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_goingEastAgainstTheWall_noPathIsProduced() {

		runTest(new Coordinates(1, 0), "E");
		
		assertEquals(0, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_goingWestAgainstTheWall_noPathIsProduced() {

		runTest(new Coordinates(0, 0), "W");
		
		assertEquals(0, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_twoInstructionsWithoutSkids_producesTwoPaths() {

		runTest(new Coordinates(0, 0), "NE");
		
		assertEquals(2, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_twoInstructionsWithoutSkids_firstPositionCorrect() {

		runTest(new Coordinates(0, 0), "NE");
		
		assertEquals(new Coordinates(0, 1), pathAccumulator.get(0));
	}
	
	@Test
	public void calculatePath_twoInstructionsWithoutSkids_secondPositionCorrect() {

		runTest(new Coordinates(0, 0), "NE");
		
		assertEquals(new Coordinates(1, 1), pathAccumulator.get(1));
	}
	
	@Test
	public void calculatePath_twoInstructionsOneCausesSkid_producesOnePath() {

		runTest(new Coordinates(0, 0), "NW");
		
		assertEquals(1, pathAccumulator.size());
	}
	
	@Test
	public void calculatePath_twoInstructionsOneCausesSkid_producesExpectSinglePath() {

		runTest(new Coordinates(0, 0), "NW");
		
		assertEquals(new Coordinates(0, 1), pathAccumulator.get(0));
	}
	
	private Coordinates runTest(Coordinates initialPosition, String instruction) {
		
		SimulationInput input = createSimulationInputWithRoom2By2(
				initialPosition, instruction);
		
		return calculator.calculatePath(input, pathAccumulator);
	}
	
	private SimulationInput createSimulationInputWithRoom2By2(
			Coordinates initialLocation, String instructions){
		
		return createSimulationInput(new Coordinates(2, 2), initialLocation, null, instructions);
	}
}
