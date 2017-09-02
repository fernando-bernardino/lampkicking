package com.yoti.test.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimulationInput {

	/**
	 * Coordinates of initial position of the robot
	 * 
	 */
	private Coordinates coords;
	
	/**
	 * Room size: [X, Y] = X axis size or width or room; Y axis size or height of the room
	 * 
	 */
	private Coordinates roomSize;
	
	/**
	 * List of locations with dirt patches
	 */
	private List<Coordinates> patches;
	
	/**
	 * Driving instructions as a sequence of moves sequences with possible values: N - north,
	 * E - east, S - south, W - west
	 */
	private String instructions;		
	
	/**
	 * Object holder for simulation input data.
	 * 
	 * @param roomSize Size of the room
	 * @param coords Initial location inside room
	 * @param patches Location of dirty patches
	 * @param instructions Driving instructions for simulation
	 * 
	 */
	@JsonCreator
	public SimulationInput(
			@JsonProperty("roomSize") Coordinates roomSize, 
			@JsonProperty("coords") Coordinates coords, 
			@JsonProperty("patches") List<Coordinates> patches,
			@JsonProperty("instructions") String instructions){
		
		this.roomSize = roomSize;
		this.coords = coords;
		this.patches = patches == null ? new ArrayList<>() : new ArrayList<>(patches);
		this.instructions = instructions == null ? "" : instructions;
	}

	public Coordinates getCoords() {
		return coords;
	}

	public Coordinates getRoomSize() {
		return roomSize;
	}
	
	public List<Coordinates> getPatches(){
		return new ArrayList<>(patches);
	}
	
	public String getInstructions(){
		return instructions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coords == null) ? 0 : coords.hashCode());
		result = prime * result + ((instructions == null) ? 0 : instructions.hashCode());
		result = prime * result + ((patches == null) ? 0 : patches.hashCode());
		result = prime * result + ((roomSize == null) ? 0 : roomSize.hashCode());
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
		SimulationInput other = (SimulationInput) obj;
		if (coords == null) {
			if (other.coords != null)
				return false;
		} else if (!coords.equals(other.coords))
			return false;
		if (instructions == null) {
			if (other.instructions != null)
				return false;
		} else if (!instructions.equals(other.instructions))
			return false;
		if (patches == null) {
			if (other.patches != null)
				return false;
		} else if (!patches.equals(other.patches))
			return false;
		if (roomSize == null) {
			if (other.roomSize != null)
				return false;
		} else if (!roomSize.equals(other.roomSize))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimulationInput [coords=" + coords + ", roomSize=" + roomSize + ", patches=" + patches
				+ ", instructions=" + instructions + "]";
	}
}
