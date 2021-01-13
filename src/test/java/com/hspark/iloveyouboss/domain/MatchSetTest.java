package com.hspark.iloveyouboss.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchSetTest {
	private Criteria criteria;
		
	private Question questionReimbursesTuition;
	private Answer answerReimbursesTuition;
	private Answer answerDoesNotReimburseTuition;
		
	private Question questionIsThereRelocation;
	private Answer answerThereIsRelocation;
	private Answer answerThereIsNoRelocation;
	
	private Question questionOnsiteDaycare;
	private Answer answerNoOnsiteDaycare;
	private Answer answerHasOnsiteDaycare;
		
	private AnswerCollection answers;
	
	@BeforeEach
	public void createAnswer() {
		answers = new AnswerCollection();
	}
		
	@BeforeEach
	public void createCriteria() {
		criteria = new Criteria();
	}
		
	@BeforeEach
	public void createQuestionsAndAnswers() {
		questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
		answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
		answerThereIsNoRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
		
		questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
		answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
		answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);
		
		questionOnsiteDaycare = new BooleanQuestion(1, "Onsite daycare?");
		answerHasOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.TRUE);
		answerNoOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.FALSE);
	}
	
	private void add(Answer answer) {
		answers.add(answer);
	}
	
	private MatchSet createMatchSet() {
		return new MatchSet(answers, criteria);
	}
	
	@Test
	void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));

		assertFalse(createMatchSet().matches());
	}
	
	@Test
	void matchAnswersTrueForAnyDontCareCriteria() throws Exception {
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));
		
		assertTrue(createMatchSet().matches());		
	}
	
	@Test
	void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
		add(answerThereIsRelocation);
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
		
		assertTrue(createMatchSet().matches());
	}
	
	@Test
	void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
		add(answerThereIsNoRelocation);
		add(answerDoesNotReimburseTuition);
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
		
		assertFalse(createMatchSet().matches());
	}
	
	@Test
	void scoreIsZeroWhenThereAreNoMatches() {
		add(answerThereIsRelocation);
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

		assertThat(createMatchSet().getScore()).isEqualTo(Weight.Important.getValue());
	}
	
	@Test
	void scoreAccumulatesCriterionValuesForMatches() {
		add(answerThereIsRelocation);
		add(answerReimbursesTuition);
		add(answerNoOnsiteDaycare);
		criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
		criteria.add(new Criterion(answerReimbursesTuition, Weight.WouldPrefer));
		criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.VeryImportant));
		
		int expectedScore = Weight.Important.getValue() + Weight.WouldPrefer.getValue();
		assertThat(createMatchSet().getScore()).isEqualTo(expectedScore);
	}

}
