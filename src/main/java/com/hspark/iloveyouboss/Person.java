package com.hspark.iloveyouboss;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class Person {
	private List<Question> characteristics = new ArrayList<>();
	
	public void add(Question characteristic) {
		characteristics.add(characteristic);
	}
	
	public List<Question> withCharacteristic(String questionPattern) {
		return characteristics.stream().filter(c -> 
				c.getText().endsWith(questionPattern)).collect(Collectors.toList());
	}
}
