package com.hspark.iloveyouboss.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
 * Profile class의 책임
 *  - 프로파일에 관한 정보 추적하기
 *  - 조건 집합이 프로파일에 매칭되는지 혹은 그 정도 파악하기
 *  
 * => 단일책임 원칙을 위배한다.
 * => 클래스를 책임 단위로 분할한다.
 */
public class Profile {
	private Map<String,Answer> answers = new HashMap<>();
	
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
	
	/*
	 * Profile class 의도와 맞지 않은 메소드
	 * - Profile 객체는 단일 점수를 갖지 ㅇ낳으며 조건과 매칭될 때만 점수가 산출된다.
	 * - matches를 호출하면 score를 저장하게 되는 부작용이 발생한다.
	 * 		- 메소드의 이름과 Profile 클래스의 의도를 생각했을때 직관에 어긋난다.
	 * 		- 의도하지 않게 score의 값을 변경할 수 있다.
	 * => 명령-질의 분리 원칙을 위반한다.
	 * 	    메소드는 1)명령을 실행(부작용을 생성하는 어떤 작업을 함)하거나 2)질의에 대답(어떤 값 반환)하는 작업을 할 수 있으며,
	 * 	  두 작업을 모두 하면 안된다.(p.192)
	 */
	/*
	public boolean matches(Criteria criteria) {
		MatchSet matchSet = new MatchSet(answers, criteria);
		score = matchSet.getScore();
		return matchSet.matches();
	}
	*/
	/*
	 * 입력받은 criteria를 바탕을 MatchSet 인스턴스를 반환하는 메소드를 생성하여
	 * score나 match 여부를 판단하는 작업은 MatchSet의 메소드를 통해 하도록 refactoring
	 */
	public MatchSet getMatchSet(Criteria criteria) {
		return new MatchSet(answers, criteria);
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