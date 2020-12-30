package com.hspark.iloveyouboss.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatCompiler {
	static Question q1 = new BooleanQuestion("Tuition reimbursement?");
	static Question q2 = new BooleanQuestion("Relocation package?");
	
	class QuestionController {
		Question find(int id) {
			if (id == 1) {
				return q1;
			}
			else {
				return q2;
			}
		}
	}
	
	private QuestionController controller = new QuestionController();
	
	public Map<String, Map<Boolean, AtomicInteger>> responsesByQuestion(
			List<BooleanAnswer> answers, Map<Integer, String> questions) {
		Map<Integer, Map<Boolean, AtomicInteger>> responses = new HashMap<>();
		answers.stream().forEach(answer -> incrementHistogram(responses, answer));
		return convertHistogramIdsToText(responses, questions);
	}

	/*
	 * 테스트: QuestionController의 find 메소드는 DB를 사용하기 때문에 빠른(Fast) 테스트를 할 수 없다.
	 * - controller에서 question을 가져오는 부분을 캡슐화 한다(questionText).
	 * 
	 */
	private Map<String, Map<Boolean, AtomicInteger>> convertHistogramIdsToText(
			Map<Integer, Map<Boolean, AtomicInteger>> responses, Map<Integer, String> questions) {
		Map<String, Map<Boolean, AtomicInteger>> textResponses = new HashMap<>();
		responses.keySet().stream().forEach(id ->
				textResponses.put(questions.get(id), responses.get(id)));
		return textResponses;
	}
	
	public Map<Integer, String> questionText(List<BooleanAnswer> answers) {
		Map<Integer, String> questions = new HashMap<>();
		answers.stream().forEach(answer -> {
			if (!questions.containsKey(answer.getQuestionId())) {
				questions.put(answer.getQuestionId(), controller.find(answer.getQuestionId()).getText());
			}
		});
		return questions;
	}

	private void incrementHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses, BooleanAnswer answer) {
		Map<Boolean, AtomicInteger> histogram = getHistogram(responses, answer.getQuestionId());
		histogram.get(Boolean.valueOf(answer.getValue())).getAndIncrement();
	}

	private Map<Boolean, AtomicInteger> getHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses,
			int id) {
		Map<Boolean, AtomicInteger> histogram = null;
		if (responses.containsKey(id)) {
			histogram = responses.get(id);
		} else {
			histogram =createNewHistogram();
			responses.put(id, histogram);
		}
		return histogram;
	}

	private Map<Boolean, AtomicInteger> createNewHistogram() {
		Map<Boolean, AtomicInteger> histogram;
		histogram = new HashMap<>();
		histogram.put(Boolean.FALSE, new AtomicInteger(0));
		histogram.put(Boolean.TRUE, new AtomicInteger(0));
		return histogram;
	}
	
}
