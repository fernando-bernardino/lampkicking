package com.yoti.test.simulate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yoti.test.entities.ErrorMessage;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

public class SimulateControllerTest {
	
	@InjectMocks
	SimulateController controller;
	
	@Mock
	SimulateService service;
	
	@Mock
	SimulationInput input;
	
	@Mock
	SimulationOutput output;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void simulate_simulateServiceThrowsAssertionError_BadRequestIsReturned(){
		
		doThrow(new IllegalArgumentException()).when(service).simulate(input);
		
		
		ResponseEntity<?> response = controller.simulate(input);
		
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void simulate_simulateServiceThrowsAssertionError_errorMessageIsInBody() {
		String errorMessage = "message";
		doThrow(new IllegalArgumentException(errorMessage)).when(service).simulate(input);
		
		
		ResponseEntity<?> response = controller.simulate(input);
		
		
		ErrorMessage responseBody = (ErrorMessage) response.getBody();
		assertTrue("Expect for " + responseBody + " to contain " + errorMessage,
				responseBody.getMessage().contains(errorMessage));
	}
}
