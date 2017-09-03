package com.yoti.test.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
	
	/**
	 * HTTP code matching the error
	 */
	private int code;
	
	/**
	 * Descriptive message about the error
	 */
	private String message;
	
	/**
	 * Holder for an error description
	 * 
	 * @param code HTTP code
	 * @param message Detail about the error
	 */
	@JsonCreator
	public ErrorMessage(
			@JsonProperty("code") int code, 
			@JsonProperty("message") String message){
		
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorMessage other = (ErrorMessage) obj;
		if (code != other.code)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ErrorMessage [code=" + code + ", message=" + message + "]";
	}

}
