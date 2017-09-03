package com.yoti.test.simulate;

import static com.yoti.test.entities.SimulationObjectsBuilder.createSimulationInput;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SimulateControllerTestIT {
	
	public static final int X = 0;
	public static final int Y = 1;
	
	@Autowired
	SimulateController simulateController;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void simulate_initialLocationOutOfRoom_BadRequestStatusReturned() {
		Coordinates roomSize = new Coordinates(2, 2);
		Coordinates initialLocation = new Coordinates(2, 1);
		SimulationInput input = createSimulationInput(roomSize, initialLocation);
		
		ResponseEntity<?> response = simulateController.simulate(input);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void simulate_invalidRoomSize_BadRequestStatusReturned() {
		Coordinates roomSize = new Coordinates(0, 0);
		Coordinates initialLocation = new Coordinates(1, 1);
		SimulationInput input = createSimulationInput(roomSize, initialLocation);
		
		ResponseEntity<?> response = simulateController.simulate(input);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void simulate_invalidIntructions_BadRequestStatusReturned() {
		Coordinates roomSize = new Coordinates(2, 2);
		Coordinates initialLocation = new Coordinates(1, 1);
		List<Coordinates> patches = createPatches(
				new int [] {0, 0},
				new int [] {0, 1});
		String instructions = "NAESW";
		SimulationInput input = createSimulationInput(roomSize, initialLocation, patches, instructions);

		ResponseEntity<?> response = simulateController.simulate(input);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void simulate_noIntructions_noPatcheCleanAndLocationIsSameAsInitial() {
		Coordinates roomSize = new Coordinates(2, 2);
		Coordinates initialLocation = new Coordinates(1, 1);
		List<Coordinates> patches = createPatches(
				new int [] {0, 0},
				new int [] {0, 1});
		String instructions = null;
		SimulationInput input = createSimulationInput(roomSize, initialLocation, patches, instructions);

		ResponseEntity<?> response = simulateController.simulate(input);
		
		assertSuccessfulRequest(response, initialLocation, 0);
	}
	
	@Test
	public void simulate_normalRequest_outputIsCorrect() {
		Coordinates roomSize = new Coordinates(5, 5);
		Coordinates initialLocation = new Coordinates(1, 2);
		List<Coordinates> patches = createPatches(
				new int [] {1, 0},
				new int [] {2, 2},
				new int [] {2, 3});
		String instructions = "NNESEESWNWW";
		SimulationInput input = createSimulationInput(roomSize, initialLocation, patches, instructions);
		
		ResponseEntity<?> response = simulateController.simulate(input);
		
		assertSuccessfulRequest(response, new Coordinates(1, 3), 1);
	}
	
	private void assertSuccessfulRequest(ResponseEntity<?> response, Coordinates expectedFinalLocation, 
			int expectedNumberPatches){
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		SimulationOutput output = (SimulationOutput) response.getBody();
		assertEquals(expectedFinalLocation, output.getCoords());
		assertEquals(expectedNumberPatches, output.getPatches());
	}
	
	private List<Coordinates> createPatches(int [] ... patches) {
		
		return Arrays.stream(patches)
					.map(
							(p) -> new Coordinates(p[X], p[Y]))
					.collect(
							Collectors.toList());
	}
}
