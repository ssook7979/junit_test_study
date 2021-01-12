package com.hspark.iloveyouboss.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
	
	private Map<String, Answer> answers = new HashMap<>();
	private int score;
	private String name;

	public Profile(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void add(Answer answer) {
		answers.put(answer.getQuestionText(), answer);
	}

	public boolean matches(Criteria criteria) {
		score = 0;
		
		boolean kill = false;
		boolean anyMatches = false;
		for (Criterion criterion: criteria) {
			/*
			 * p.171 - 172
			 * 리팩토링한 코드는 디메테르의 법칙(the Law of Demeter)를 위반하지 않음
			 * 디메테르의 법칙(the Law of Demeter): 다른 객체로 전파되는 연쇄적인 메소드 호출을 피해야 함
			 * 
			 * answer 임시변수로 추출
			 * 
			 *  **임시변수의 활용**
			 *  - 값 비싼 비용의 계산값을 캐시에 저장
			 *  - 메소드 몸체에서 변경되는 것들을 수집
			 *  - 코드의 의도를 명확하게 함
			 */
			
			// answer 변수가 코드를 더 명확하게 해주지 않고 한번만 사용되므로 굳이 변수로 추출하지 않아도 된다.
			// -> inline 코드로 변환
			boolean match = criterion.matches(answerMatching(criterion));
			
			if (!match && criterion.getWeight() == Weight.MustMatch) {
				kill = true;
			}
			if (match) {
				score += criterion.getWeight().getValue();
			}
			anyMatches |= match;
		}
		if (kill) {
			return false;
		}
		return anyMatches;
	}

	private Answer answerMatching(Criterion criterion) {
		return answers.get(criterion.getAnswer().getQuestionText());
	}
	
	public int score() {
		return score;
	}

	public List<Answer> classicFind(Predicate<Answer> pred) {
	   List<Answer> results = new ArrayList<Answer>();
	   for (Answer answer: answers.values())
	      if (pred.test(answer))
	         results.add(answer);
	   return results;
	}
	
	@Override
	public String toString() {
	  return name;
	}
	
	public List<Answer> find(Predicate<Answer> pred) {
	   return answers.values().stream()
	         .filter(pred)
	         .collect(Collectors.toList());
	}
}
