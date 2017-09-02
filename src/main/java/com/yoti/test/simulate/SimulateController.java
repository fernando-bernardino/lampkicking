package com.yoti.test.simulate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoti.test.entities.ErrorMessage;
import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

@RestController
@RequestMapping("/api/v1")
public class SimulateController {
	
	@Autowired
	SimulateService simulateService;
	
	@RequestMapping(value = "/simulate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<? extends Object> simulate(@RequestBody SimulationInput input) {
		
		try {
			
			SimulationOutput output = simulateService.simulate(input);
		
			return new ResponseEntity<SimulationOutput>(output, HttpStatus.CREATED);
			
		} catch (IllegalArgumentException error) {
			
			return new ResponseEntity<ErrorMessage>(
					new ErrorMessage(HttpStatus.BAD_REQUEST.value(), error.getMessage()), 
					HttpStatus.BAD_REQUEST);
		}
			
    }

}
