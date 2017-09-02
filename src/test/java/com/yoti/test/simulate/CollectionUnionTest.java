package com.yoti.test.simulate;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CollectionUnionTest {

	CollectionUnion setIntersection = new CollectionUnion();
	
	Set<String> left = new HashSet<>();
	
	Set<String> right = new HashSet<>();
	
	@Test
	public void countMatches_setsAreDifferent_returnsZero() {
		
		left.add("noMatch1");
		right.add("noMatch2");
		
		int returned = setIntersection.countMatches(left, right);
		
		assertEquals(0, returned);
	}
	
	@Test
	public void countMatches_oneNotInCommon_returnedOne() {
		
		left.add("match");
		left.add("noMatch1");
		right.add("match");
		right.add("noMatch2");
		
		int returned = setIntersection.countMatches(left, right);
		
		assertEquals(1, returned);
	}
}
