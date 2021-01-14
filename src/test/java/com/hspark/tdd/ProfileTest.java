package com.hspark.tdd;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileTest {
	
	private Profile profile;
	private Question questionIsThereRelocation;
	private Answer answerThereIsRelocation;
	private Answer answerThereIsNotRelocation;
	
	@BeforeEach
	public void createProfile() {
		profile = new Profile();
	}
	
	@BeforeEach
	public void createQuestionAndAnswer() {
		questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
		answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
		answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.False);
	}
	/*
	 * 시나리오에 따라 실패하는 테스트 작성
	 * 1. BooleanQuestion 타입의 Question을 Criterion에 등록
	 * 2. Profile에 기준에 맞는 Answer이 없는 경우 False 반환
	 */
	@Test
	void matchesNothingWhenProfileEmpty() {			
		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);
		
		boolean result = profile.matches(criterion);
		
		assertFalse(result);
	}
	
	/*
	 * 시나리오
	 * Profile에 answer를 추가하고 추가된 것이 criterion의 조건에 맞는지 검사하는 로직을 실행한다.
	 * 조건 1) profile의 answer이 null이면 안된다. -> 이 조건은 테스트들이 모두 통과할 수 있는 조건이다.
	 */
	@Test
	void matchesWhenProfileContainsMatchingAnswer() {
		Answer answer = new Answer(questionIsThereRelocation, Bool.TRUE);
		profile.add(answer);
		Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
		
		boolean result = profile.matches(criterion);
		
		assertTrue(result);
	}
	
	@Test
	void doesNotMatchWhenNoMatchingAnswer() {
		profile.add(answerThereIsNotRelocation);
		Criterion criterion = new Criterion(answerThereIsNotRelocation, Weight.Important);
		
		boolean result = profile.matches(criterion);
		
		assertFalse(result);
	}

}
