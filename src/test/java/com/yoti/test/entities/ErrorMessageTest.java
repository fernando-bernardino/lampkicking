package com.yoti.test.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ErrorMessageTest {

	@Test
	public void ErrorMessage_codeIsSet() {
		int code = 1;
		String message = "message";
		ErrorMessage error = new ErrorMessage(code, message);
		
		assertEquals(code, error.getCode());
	}

	@Test
	public void ErrorMessage_messageIsSet() {
		int code = 1;
		String message = "message";
		ErrorMessage error = new ErrorMessage(code, message);
		
		assertEquals(message, error.getMessage());
	}
}
