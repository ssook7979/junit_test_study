package com.hspark.tdd;

import java.util.HashMap;
import java.util.Map;

public class Profile {

	private Map<String, Answer> answers = new HashMap<>();

	public boolean matches(Criterion criterion) {
		return answer != null && answer.match(criterion.getAnswer());
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

}
