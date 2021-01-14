package com.hspark.tdd;

import java.util.HashMap;
import java.util.Map;

public class Profile {

	private Map<String, Answer> answers = new HashMap<>();

	public boolean matches(Criterion criterion) {
		Answer answer = getMatchingPrfileAnswer(criterion);
		return answer != null && answer.match(criterion.getAnswer());
	}

	/*
	 * 인터페이스 확장
	 * - 처음엔 간단히 Criterion을 가지고 코드를 작성해본다. 코드가 완성되면 Criteria로 확장한다.
	 * 
	 */
	public boolean matches(Criteria criteria) {
		for (Criterion criterion: criteria) {
			if (criterion.getWeight() == Weight.MustMatch) return false;
			if (matches(criterion)) return true;
		}
		return false;
	}

	private Answer getMatchingPrfileAnswer(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

}
