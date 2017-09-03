package com.yoti.test.simulate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoti.test.entities.SimulationInput;

@RestController
@RequestMapping("/api/v1")
public class SimulateController {
	
	@Autowired
	SimulateServiceDecorator simulateServiceDecorator;
	
	@RequestMapping(value = "/simulate", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<? extends Object> simulate(@RequestBody SimulationInput input) {
		
		return simulateServiceDecorator.simulate(input);
    }

}
