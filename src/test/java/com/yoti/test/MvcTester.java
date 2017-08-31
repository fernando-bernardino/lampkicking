package com.yoti.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MvcTester {
	
	@Autowired
    private MockMvc mvc;
    
    ObjectMapper objectMapper = new ObjectMapper();

    
	public <M> void executePostTest(String path, HttpStatus expectedStatus, M requestObject) throws Exception {
		
		mvc.perform(
					post(path)
    					.content(objectMapper.writeValueAsBytes(requestObject))
    					.contentType(MediaType.APPLICATION_JSON)
    					.accept(MediaType.APPLICATION_JSON))
		
				.andExpect(status().is(expectedStatus.value()));
	}
}
