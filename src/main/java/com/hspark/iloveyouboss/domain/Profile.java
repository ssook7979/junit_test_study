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
		
		// matches 메소드의 핵심 목표
		// Refactoroing Point: 3의 목표가 명확하게 드러나지 않는다.
		for (Criterion criterion: criteria) {
			boolean match = criterion.matches(answerMatching(criterion));
			
			if (!match && criterion.getWeight() == Weight.MustMatch) {
				kill = true;
			}
			if (match) {
				// 1. 매칭되는 조건의 가중치를 합하여 점수를 계산한다.
				score += criterion.getWeight().getValue();
			}
		}
		if (kill) {
			// 2. weight가 MustMatch일 경우 프로파일 답변과 매칭되지 않으면 false 반환
			return false;
		}
		// 3. weight가 MustMatch가 아닐 경우 프로파일 답변과 매칭되면 true 반환
		return anyMatches(criteria);
	}

	private boolean anyMatches(Criteria criteria) {
		boolean anyMatches = false;
		for (Criterion criterion: criteria) {
			anyMatches |= criterion.matches(answerMatching(criterion));
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
