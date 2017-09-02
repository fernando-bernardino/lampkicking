package com.yoti.test.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CoordinatesTest {

	@Test
	public void Location_coordinatesGiven_xWellSet(){
		int x = 1;
		
		Coordinates location = new Coordinates(x, 2);
		
		assertEquals(x, location.getX());
	}

	@Test
	public void Location_coordinatesGiven_yWellSet(){
		int y = 1;
		
		Coordinates location = new Coordinates(2, y);
		
		assertEquals(y, location.getY());
	}
}
