package com.yoti.test.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationOutput {
	
	/**
	 * Final coordinates in (X, Y) format
	 */
	private Coordinates coords;
	
	/**
	 * Number of patches not cleaned during simulation
	 */
	private int patches;
	
	
	/**
	 * Object holder for simulation output data.
	 * 
	 * @param coords Final position of the robot on the end of simulation
	 * @param patches Number of patches left unclean
	 * 
	 */
	@JsonCreator
	public SimulationOutput(
			@JsonProperty("coords") Coordinates coords, 
			@JsonProperty("patches") int patches) {
		
		this.coords = coords;
		this.patches = patches; 
	}

	public Coordinates getCoords() {
		return coords;
	}

	public int getPatches() {
		return patches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
		result = prime * result + patches;
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
		SimulationOutput other = (SimulationOutput) obj;
		if (coords == null) {
			if (other.coords != null)
				return false;
		} else if (!coords.equals(other.coords))
			return false;
		if (patches != other.patches)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimulationOutput [coords=" + coords + ", patches=" + patches + "]";
	}
	
}
