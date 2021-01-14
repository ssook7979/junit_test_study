package com.hspark.iloveyouboss.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hspark.iloveyouboss.domain.Question;

class QuestionControllerTest {

	private QuestionController controller;
	
	@BeforeEach
	public void create() {
		controller = new QuestionController();
		controller.deleteAll();
	}
	
	@Test
	void findPersistedQuestionById() {
		int id = controller.addBooleanQuestion("question text");
		
		Question question = controller.find(id);
		
		assertThat(question.getText()).isEqualTo("question text");
	}
	
	@Test
	void questionAnswersDateAdded() {
		Instant now = new Date().toInstant();
		controller.setClock(Clock.fixed(now,  ZoneId.of("America/Denver")));
		int id = controller.addBooleanQuestion("text");
		
		Question question = controller.find(id);
		
		// assertThat(question.getCreateTimestamp()).isEqualTo(now);
	}
	
	@Test
	void answersMultiplePersistedQuestions() {
		controller.addBooleanQuestion("q1");
		controller.addBooleanQuestion("q2");
		controller.addPercentileQuestion("q", new String[] {"a1", "a2"});
		
		List<Question> questions = controller.getAll();
		
		assertThat(
				questions.stream().map(Question::getText).collect(Collectors.toList())
		).isEqualTo(Arrays.asList(new String[] {"q1", "q2", "q3"}));
	}
	
	@Test
	void findsMatchingEntries() {
		controller.addBooleanQuestion("alpha 1");
		controller.addBooleanQuestion("alpha 2");
		controller.addBooleanQuestion("beta 2");
		
		List<Question> questions = controller.findWithMatchingText("alpha");
		
		assertThat(questions.stream().map(Question::getText).collect(Collectors.toList()))
			.isEqualTo(Arrays.asList(new String[] {"alpha 1", "alpha 2"}));
	}

}
