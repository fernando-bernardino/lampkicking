package com.yoti.test.simulate;

import static com.yoti.test.entities.SimulationObjectsBuilder.createSimulationInput;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;

public class SimulationInputValidatorTest {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	
	SimulationInputValidator validator;
	
	
	@Before
	public void setup(){
		validator = new SimulationInputValidator();
	}
	
	@Test
	public void validate_xInitialLocationOutsideRoom_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(5, 5);
		Coordinates coords = new Coordinates(5, 0);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("location");
		
		validator.validate(input);
	}
	
	@Test
	public void validate_yInitialLocationOutsideRoom_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(5, 5);
		Coordinates coords = new Coordinates(0, 5);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("location");
	
		validator.validate(input);
	}
	
	@Test
	public void validate_xInitialNegative_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(5, 5);
		Coordinates coords = new Coordinates(-1, 1);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("location");
		
		validator.validate(input);
	}
	
	@Test
	public void validate_yInitialNegative_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(5, 5);
		Coordinates coords = new Coordinates(1, -1);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("location");
		
		validator.validate(input);
	}
	
	@Test
	public void validate_roomXSizeZero_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(0, 5);
		Coordinates coords = new Coordinates(0, 0);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("size");
		
		validator.validate(input);
	}
	
	@Test
	public void validate_roomYSizeZero_throwInvalidArgumentException(){
		Coordinates roomSize = new Coordinates(5, 0);
		Coordinates coords = new Coordinates(0, 0);
		SimulationInput input = createSimulationInput(roomSize, coords);
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("size");
		
		validator.validate(input);
	}
}
