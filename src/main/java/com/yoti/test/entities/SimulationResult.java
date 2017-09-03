package com.yoti.test.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationResult {

	/**
	 * Input provided to the simulation
	 */
	private SimulationInput input;

	/**
	 * Output produced by the simulation
	 */
	private SimulationOutput output;
	
	/**
	 * Holder for a successful simulation data.
	 * 
	 * @param input Simulation input
	 * @param output Simulation output
	 * 
	 */
	@JsonCreator
	public SimulationResult(
			@JsonProperty("input") SimulationInput input,
			@JsonProperty("output") SimulationOutput output){
		
		this.input = input;
		this.output = output;
	}
	
	public SimulationInput getInput() {
		return input;
	}

	public SimulationOutput getOutput() {
		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((input == null) ? 0 : input.hashCode());
		result = prime * result + ((output == null) ? 0 : output.hashCode());
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
		SimulationResult other = (SimulationResult) obj;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimulationResult [input=" + input + ", output=" + output + "]";
	}
}
