package com.hspark.tdd;

import java.util.HashMap;
import java.util.Map;

public class Profile {

	private Map<String, Answer> answers = new HashMap<>();

	public boolean matches(Criterion criterion) {
		Answer answer = getMatchingPrfileAnswer(criterion);
		return answer != null && answer.match(criterion.getAnswer());
	}

	private Answer getMatchingPrfileAnswer(Criterion criterion) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

}
