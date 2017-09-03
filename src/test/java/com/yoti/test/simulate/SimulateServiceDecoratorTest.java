package com.yoti.test.simulate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static com.yoti.test.entities.SimulationObjectsBuilder.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yoti.test.db.SimulationErrorAsyncWriter;
import com.yoti.test.db.SimulationResultAsyncWriter;
import com.yoti.test.entities.ErrorMessage;
import com.yoti.test.entities.SimulationError;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;
import com.yoti.test.entities.SimulationResult;

public class SimulateServiceDecoratorTest {
	
	@InjectMocks
	private SimulateServiceDecorator decorator;
	
	@Mock
	private SimulateService service;
	
	@Mock
	private SimulationResultAsyncWriter simulationResultAsyncWriter;
	
	@Mock
	private SimulationErrorAsyncWriter simulationErrorAsyncWriter;
	
	@Captor
	ArgumentCaptor<SimulationResult> resultCaptor;
	
	@Captor
	ArgumentCaptor<SimulationError> errorCaptor;
	
	
	private SimulationInput input;
	

	private SimulationOutput output;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		input = createMeaninfulSimulationInput();
		output = createMeaninfulSimulationOutput();
	}

	@Test
	public void simulate_simulateServiceThrowsAssertionError_BadRequestIsReturned(){
		
		doThrow(new IllegalArgumentException()).when(service).simulate(input);
		
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void simulate_simulateServiceThrowsAssertionError_inputRecorded() throws Exception {
		
		doThrow(new IllegalArgumentException()).when(service).simulate(input);
		
		decorator.simulate(input);
		
		assertEquals(input, getWrittenSimulationError().getInput());
	}
	
	@Test
	public void simulate_simulateServiceThrowsAssertionError_error400Recorded() throws Exception {
		
		doThrow(new IllegalArgumentException()).when(service).simulate(input);
		
		decorator.simulate(input);
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), getWrittenSimulationError().getError().getCode());
	}

	@Test
	public void simulate_simulateServiceThrowsAssertionError_errorMessageIsInBody() {
		String errorMessage = "message";
		doThrow(new IllegalArgumentException(errorMessage)).when(service).simulate(input);
		
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		
		ErrorMessage responseBody = (ErrorMessage) response.getBody();
		assertTrue("Expect for " + responseBody + " to contain " + errorMessage,
				responseBody.getMessage().contains(errorMessage));
	}

	@Test
	public void simulate_simulateServiceThrowsGenericException_InternalServerErrorIsReturned() {
		
		doThrow(new RuntimeException()).when(service).simulate(input);
		
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void simulate_simulateServiceThrowsGenericException_errorMessageIsInBody() {
		String errorMessage = "message";
		doThrow(new RuntimeException(errorMessage)).when(service).simulate(input);
		
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		
		ErrorMessage responseBody = (ErrorMessage) response.getBody();
		assertTrue("Expect for " + responseBody + " to contain " + errorMessage,
				responseBody.getMessage().contains(errorMessage));
	}
	
	@Test
	public void simulate_simulateServiceThrowsGenericException_inputRecorded() throws Exception {
		
		doThrow(new RuntimeException()).when(service).simulate(input);
		
		decorator.simulate(input);
		
		assertEquals(input, getWrittenSimulationError().getInput());
	}
	
	@Test
	public void simulate_simulateServiceThrowsGenericException_error500Recorded() throws Exception {
		
		doThrow(new RuntimeException()).when(service).simulate(input);
		
		decorator.simulate(input);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), getWrittenSimulationError().getError().getCode());
	}
	
	@Test
	public void simulate_successfulSimulation_resultOutput() {
		
		doReturn(output).when(service).simulate(input);
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		assertEquals(output, (SimulationOutput) response.getBody());
	}
	
	@Test
	public void simulate_successfulSimulation_result201CreatedHttpStatus() {
		
		doReturn(output).when(service).simulate(input);
		
		ResponseEntity<?> response = decorator.simulate(input);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void simulate_successfulSimulation_resultsRecorded() throws Exception {
		
		doReturn(output).when(service).simulate(input);
		
		decorator.simulate(input);
		
		assertEquals(new SimulationResult(input, output), getWrittenSimulationResult());
	}

	private SimulationResult getWrittenSimulationResult() throws Exception {
		
		verify(simulationResultAsyncWriter).write(resultCaptor.capture(), any());
		
		return resultCaptor.getValue();
	}

	private SimulationError getWrittenSimulationError() throws Exception {
		
		verify(simulationErrorAsyncWriter).write(errorCaptor.capture(), any());
		
		return errorCaptor.getValue();
	}
}
