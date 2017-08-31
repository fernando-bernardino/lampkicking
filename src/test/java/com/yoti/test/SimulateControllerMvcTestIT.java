package com.yoti.test;

import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulateController.class)
public class SimulateControllerMvcTestIT extends MvcTester {

    @MockBean
	protected SimulateController simulateController;
	
	@Test
	public void simulate_validRequestBody_returnsCreated() throws Exception {
		SimulationInput input = new SimulationInput();
		ResponseEntity<SimulationOutput> response = new ResponseEntity<>(new SimulationOutput(), HttpStatus.CREATED); 
		
		given(simulateController.simulate(input)).willReturn(response);
		
		executePostTest("/api/v1/simulate", HttpStatus.CREATED, input);
	}

}
