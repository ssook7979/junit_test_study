package com.hspark.iloveyouboss;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hspark.iloveyouboss.domain.Answer;
import com.hspark.iloveyouboss.domain.Bool;
import com.hspark.iloveyouboss.domain.BooleanQuestion;
import com.hspark.iloveyouboss.domain.Criteria;
import com.hspark.iloveyouboss.domain.Criterion;
import com.hspark.iloveyouboss.domain.Profile;
import com.hspark.iloveyouboss.domain.Weight;

class ProfileTest {
	private Profile profile;
	private BooleanQuestion question;
	private Criteria criteria;
	
	@BeforeEach
	public void create() {
		profile = new Profile("Bull Hockey, Inc.");
		question = new BooleanQuestion(1, "Got bonuses?");
		criteria = new Criteria();
	}

	@Test
	void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
		profile.add(new Answer(question, Bool.FALSE));
		criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));
		
		boolean matches = profile.matches(criteria);
		
		assertFalse(matches);
	}
	
	@Test
	public void matchAnswersTrueForAnyDontCareCriteria() throws Exception {
		profile.add(new Answer(question, Bool.FALSE));
		criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));
		
		boolean matches = profile.matches(criteria);
		
		assertTrue(matches);		
	}

}
