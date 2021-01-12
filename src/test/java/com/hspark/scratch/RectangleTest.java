package com.hspark.scratch;

import static com.hspark.scratch.ConstrainsSidesTo.constrainsSidesTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.awt.Point;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RectangleTest {
	private Rectangle rectangle;
	
	@AfterEach
	public void ensureInvariant() {
		org.hamcrest.MatcherAssert.assertThat(rectangle, constrainsSidesTo(100));
	}

	@Test
	void ansersArea() {
		rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
		assertThat(rectangle.area()).isEqualTo(50);
	}
	
	@Test
	void answersArea() {
		rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
		assertThat(rectangle.area()).isEqualTo(50);
	}
	
	@Test
	void allowsDynamicallyChangingSize() {
		rectangle = new Rectangle(new Point(5, 5));
		rectangle.setOppositeCorner(new Point(130, 130));
		assertThat(rectangle.area()).isEqualTo(15625);
	}

}
