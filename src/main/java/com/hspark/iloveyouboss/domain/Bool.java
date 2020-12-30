package com.hspark.iloveyouboss.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bool {
	False(0),
	True(1);
	public static final int FALSE = 0;
	public static final int TRUE = 1;
	private int value;
}
