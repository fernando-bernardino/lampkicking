package com.yoti.test.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationError {

	/**
	 * Input provided to the simulation
	 */
	private SimulationInput input;

	/**
	 * Error occurred during simulation information
	 */
	private ErrorMessage error;
	
	/**
	 * Holder for a successful simulation data.
	 * 
	 * @param input Simulation input
	 * @param error Error details
	 * 
	 */
	@JsonCreator
	public SimulationError(
			@JsonProperty("input") SimulationInput input,
			@JsonProperty("error") ErrorMessage error){
		
		this.input = input;
		this.error = error;
	}
	
	public SimulationInput getInput() {
		return input;
	}

	public ErrorMessage getError() {
		return error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((input == null) ? 0 : input.hashCode());
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
		SimulationError other = (SimulationError) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimulationError [input=" + input + ", error=" + error + "]";
	}
}
