package com.hspark.tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AnswerTest {
	
	@Test
	void matchAgainstNullAnswerReturnsFalse() {
		assertFalse(new Answer(new BooleanQuestion(0, ""), Bool.TRUE).match(null));
	}

}
