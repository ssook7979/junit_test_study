package com.hspark.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SparseArrayTest {
	private SparseArray<String> array;
	
	@BeforeEach
	public void create() {
		array = new SparseArray<>();
	}
	
	@Test
	public void answersElementAtIndex() {
		array.put(5, "five");
		assertThat(array.get(5)).isEqualTo("five");
	}

	@Test
	void test() {
		array.put(7, "seven");
		assertThrows(InvariantException.class,  () -> array.checkInvariants());
		array.put(6, "six");
		array.checkInvariants();
		assertThat(array.get(6)).isEqualTo("six");
		assertThat(array.get(7)).isEqualTo("seven");
	}

}
