package com.hspark.tdd;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ProfileTest {
	/*
	 * 시나리오에 따라 실패하는 테스트 작성
	 * 1. BooleanQuestion 타입의 Question을 Criterion에 등록
	 * 2. Profile에 기준에 맞는 Answer이 없는 경우 False 반환
	 */
	@Test
	void matchesNothingWhenProfileEmpty() {
		Profile profile = new Profile();
		Question question = new BooleanQuestion(1, "Relocation package?");
		Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);
		
		boolean result = profile.matches(criterion);
		
		assertFalse(result);
	}

}
