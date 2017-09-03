package com.yoti.test.simulate;

import static com.yoti.test.entities.SimulationObjectsBuilder.createSimulationInput;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

public class SimulateServiceTest {
	
	@InjectMocks
	SimulateService service;
	
	@Mock
	SimulationInputValidator simulationInputValidator;
	
	@Mock
	PathCalculator pathCalculator;
	
	@Mock
	CollectionUnion interception;
	
	@Captor
	ArgumentCaptor<SimulationInput> inputCaptor;
	
	@Captor
	ArgumentCaptor<Collection<Coordinates>> patchesCaptor;
	
	
	Coordinates expectedFinalLocation = new Coordinates(0, 0);
	
	
	int expectedNumberPatches = 1;
	
	@Before
	public void setup(){
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void simulate_inputIsValidated() {
		
		SimulationInput input = setupMocksWithSimulationInput();
		
		service.simulate(input);
		
		verify(simulationInputValidator).validate(input);
	}
	
	@Test
	public void simulate_finalLocationIsCorrect() {

		SimulationInput input = setupMocksWithSimulationInput();
		
		SimulationOutput output = service.simulate(input);
		
		assertEquals(expectedFinalLocation, output.getCoords());
	}
	
	@Test
	public void simulate_inputWasPassedForPathCreation() {

		SimulationInput input = setupMocksWithSimulationInput();
		
		service.simulate(input);
		
		assertEquals(input, getPassedInput());
	}
	
	@Test
	public void simulate_patchesWerePassedForCountingCleanedPatches() {

		SimulationInput input = setupMocksWithSimulationInput();
		
		service.simulate(input);
		
		assertEquals(input.getPatches(), getPassedPatches());
	}

	@Test
	public void simulate_numberPatchesWellSet() {

		SimulationInput input = setupMocksWithSimulationInput();
		
		SimulationOutput output = service.simulate(input);
		
		assertEquals(expectedNumberPatches, output.getPatches());
	}

	@SuppressWarnings("unchecked")
	private SimulationInput setupMocksWithSimulationInput() {
		
		SimulationInput input = createMeaningfulSimulationInput();
		
		doReturn(expectedFinalLocation).when(pathCalculator).calculatePath((SimulationInput) any(), (Collection<Coordinates>) any());
		doReturn(expectedNumberPatches).when(interception).countMatches((Collection<Coordinates>) any(), (Collection<Coordinates>) any());
		
		return input;
	}

	@SuppressWarnings("unchecked")
	private SimulationInput getPassedInput() {

		verify(pathCalculator).calculatePath(inputCaptor.capture(), (Collection<Coordinates>) any());
		
		return inputCaptor.getValue();
	}
	
	@SuppressWarnings("unchecked")
	private Collection<Coordinates> getPassedPatches() {
		
		verify(interception).countMatches((Collection<Coordinates>) any(), patchesCaptor.capture());
		
		return patchesCaptor.getValue();
	}
	
	private SimulationInput createMeaningfulSimulationInput() {
		
		List<Coordinates> patches = new ArrayList<>(1);
		patches.add(new Coordinates(1, 2));
		
		return createSimulationInput(
				new Coordinates(2, 2), 
				new Coordinates(1, 1), 
				patches, 
				"directions");
	}
}
