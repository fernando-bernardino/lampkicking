package com.yoti.test.entities;

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
	public SimulationOutput(Coordinates coords, int patches) {
		this.coords = coords;
		this.patches = patches; 
	}

	public Coordinates getCoords() {
		return coords;
	}

	public int getPatches() {
		return patches;
	}
}
