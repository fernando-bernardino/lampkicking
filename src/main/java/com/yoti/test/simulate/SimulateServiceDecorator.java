package com.yoti.test.simulate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.async.SingleResultCallback;
import com.yoti.test.db.SimulationErrorAsyncWriter;
import com.yoti.test.db.SimulationResultAsyncWriter;
import com.yoti.test.entities.ErrorMessage;
import com.yoti.test.entities.SimulationError;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;
import com.yoti.test.entities.SimulationResult;

@Service
public class SimulateServiceDecorator {
	
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	SimulateService simulateService;
	
	@Autowired
	SimulationResultAsyncWriter simulationResultAsyncWriter;
	
	@Autowired
	SimulationErrorAsyncWriter simulationErrorAsyncWriter;
	
	
    public ResponseEntity<? extends Object> simulate(SimulationInput input) {
		
		try {
			
			SimulationOutput output = simulateService.simulate(input);
			
			return processSuccessfulRequest(input, output);
			
		} catch (IllegalArgumentException error) {
			
			return processRequestError(input, HttpStatus.BAD_REQUEST, error.getMessage());
			
		} catch (Exception genericException) {
			
			return processRequestError(input, HttpStatus.INTERNAL_SERVER_ERROR, genericException.getMessage());
			
		}
    }

	private ResponseEntity<? extends Object> processSuccessfulRequest(SimulationInput input, SimulationOutput output)
			throws Exception {
		
		persist(input, output);

		return new ResponseEntity<SimulationOutput>(output, HttpStatus.CREATED);
	}
	
	private ResponseEntity<? extends Object> processRequestError(SimulationInput input, HttpStatus code, String message) {
		
		ErrorMessage errorMessage = new ErrorMessage(code.value(), message);
		
		persist(input, errorMessage);
		
		return new ResponseEntity<ErrorMessage>(
				errorMessage, 
				code);
	}

	
	private SingleResultCallback<Void> persistenceCallbackLogger = (result, throwable) -> {
		
		if(throwable != null) {
			log.warn("Error writing to the database", throwable);
		}
	};

	private void persist(SimulationInput input, SimulationOutput output) throws Exception {
		
		simulationResultAsyncWriter.write(new SimulationResult(input, output), persistenceCallbackLogger);
	}	

	private void persist(SimulationInput input, ErrorMessage errorMessage) {
		
		try {
			
			simulationErrorAsyncWriter.write(new SimulationError(input, errorMessage), persistenceCallbackLogger);
			
		} catch (Exception e) {
			
			log.warn("Error writing error to database", e);
		}
	}	
}
