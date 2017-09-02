package com.yoti.test.simulate;

import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public class CollectionUnion {

	public <M> int countMatches(Collection<M> left, Collection<M> right) {
		
		return (int) right.stream()
				.filter(
						(e) -> left.contains(e))
				.count();
	}
}
