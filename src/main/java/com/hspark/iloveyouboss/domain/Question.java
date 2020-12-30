package com.hspark.iloveyouboss.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Question")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@NoArgsConstructor
public abstract class Question implements Serializable, Persistable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(updatable=false, nullable=false)
	private Integer id;
	
	@Column
	private String text;
	
	@Column
	private Instant instant;
	
	public Question(String text) {
		this.text = text;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
	      StringBuilder s = new StringBuilder("Question #" + getId() + ": " + getText());
	      getAnswerChoices().stream().forEach((choice) -> s.append("\t" + choice));
	      return s.toString();
	}
	
	public String getAnswerChoice(int i) {
		return getAnswerChoices().get(i);
	}
	
	public boolean match(Answer answer) {
		return false;
	}
	
	abstract public boolean match(int expected, int actual);
	abstract public List<String> getAnswerChoices();
	
	public int indexOf(String matchingAnswerChoice) {
		for (int i = 0; i < getAnswerChoices().size(); i++) {
			if (getAnswerChoice(i).equals(matchingAnswerChoice)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public Instant getCreateTimestamp() {
		return instant;
	}
	
	@Override
	public void setCreateTimestamp(Instant instant) {
		this.instant = instant;
	}
	
}
