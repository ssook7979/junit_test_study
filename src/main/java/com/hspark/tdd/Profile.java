package com.hspark.tdd;

import java.util.HashMap;
import java.util.Map;

public class Profile {

	private Map<String, Answer> answers = new HashMap<>();

	public boolean matches(Criterion criterion) {
		Answer answer = getMatchingPrfileAnswer(criterion);
		return answer != null && answer.match(criterion.getAnswer());
	}

	public Answer getMatchingPrfileAnswer(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

}
