package com.hspark.scratch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BearingTest {

	@Test
	void throwsOnNegativeNumber() {
		assertThrows(BearingOutOfRangeException.class, () -> new Bearing(-1));
	}
	
	@Test
	void throwsWhenBearingTooLarge() {
		assertThrows(BearingOutOfRangeException.class, () -> new Bearing(Bearing.MAX + 1));
	}
	
	@Test
	void answersValidBearing() {
		assertThat(new Bearing(Bearing.MAX).value()).isEqualTo(Bearing.MAX);
	}
	
	@Test
	void answersAngleBetweenItAndAnotherBearing() {
		assertThat(new Bearing(15).angleBetween(new Bearing(12))).isEqualTo(3);
	}
	
	@Test
	void angleBetweenIsNegativeWhenThisBearingSmaller() {
		assertThat(new Bearing(12).angleBetween(new Bearing(15))).isEqualTo(-3);
	}

}
