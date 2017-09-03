package com.yoti.test.simulate;

import static com.yoti.test.entities.SimulationObjectsBuilder.createEmptySimulationInput;
import static org.mockito.BDDMockito.willReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoti.test.entities.Coordinates;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;
import com.yoti.test.util.MvcTester;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulateController.class)
public class SimulateControllerMvcTestIT extends MvcTester {

    @MockBean
	protected SimulateController simulateController;
	
	@Test
	public void simulate_validRequestBody_returnsCreated() throws Exception {
		
		SimulationInput input = createEmptySimulationInput();
		ResponseEntity<?> response = new ResponseEntity<>(new SimulationOutput(new Coordinates(0, 0), 0), HttpStatus.CREATED); 
		willReturn(response).given(simulateController).simulate(input);
		
		
		executePostTest("/api/v1/simulate", HttpStatus.CREATED, input);
	}

}
