package com.yoti.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoti.test.entities.SimulationInput;
import com.yoti.test.entities.SimulationOutput;

@RestController
@RequestMapping("/api/v1")
public class SimulateController {
	
	@RequestMapping(value = "/simulate", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<SimulationOutput> simulate(@RequestBody SimulationInput input) {
		
		return new ResponseEntity<SimulationOutput>(
				new SimulationOutput(), 
				HttpStatus.CREATED);
    }

}
