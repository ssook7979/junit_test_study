package com.hspark.util;

import java.util.Arrays;
import java.util.Objects;

// TODO: SparseArray 에대해 공부하고 결함 수정해보기
public class SparseArray<T> {
	public static final int INITIAL_SIZE = 1000;
	private int[] keys = new int[INITIAL_SIZE];
	private Object[] values = new Object[INITIAL_SIZE];
	private int size = 0;
	
	public void put(int key, T value) {
		if (value == null) return;
		
		int index = binarySearch(key, keys, size);
		if (index != -1 && keys[index] == key) {
			values[index] = value;
		} else {
			insertAfter(key, value, index);
		}
	}
	
	private void insertAfter(int key, T value, int index) {
		int[] newKeys = new int[INITIAL_SIZE];
		Object[] newValues = new Object[INITIAL_SIZE];
		copyFromBefore(index, newKeys, newValues);
		
		int newIndex = index + 1;
		newKeys[newIndex] = key;
		newValues[newIndex] = value;
		
		if (size - newIndex != 0) {
			copyFromAfter(index, newKeys, newValues);
			keys = newKeys;
			values = newValues;
		}
	}

	private void copyFromAfter(int index, int[] newKeys, Object[] newValues) {
		int start = index + 1;
		System.arraycopy(keys, start, newKeys, start + 1, size - start);
		System.arraycopy(values, start, newValues, start + 1, size - start);	
	}

	private void copyFromBefore(int index, int[] newKeys, Object[] newValues) {
		System.arraycopy(keys, 0, newKeys, 0, index + 1);
		System.arraycopy(values, 0, newValues, 0, index + 1);
	}

	public T get(int key) {
		int index = binarySearch(key, keys, size);
		if (index != -1 && keys[index] == key) {
			return (T) values[index];
		}
		return null;
	}
	
	private int binarySearch(int n, int[] nums, int size) {
		int low = 0;
		int high = size -1;
		
		while (low <= high) {
			int midIndex = (low + high) / 2;
			if (n > nums[midIndex]) {
				low = midIndex + 1;
			} else if (n <nums[midIndex]) {
				high = midIndex - 1;
			} else {
				return midIndex;
			}
		}
		return low - 1;
	}

	public int size() {
		return size;
	}
	
	// 불변 메소드를 내장하여 범위 테스트
	// 뷸필요하게 내부 구현 사항을 노출하지 않고 결함을 알 수 있다.
	public void checkInvariants() {
		long nonNullValues = Arrays.stream(values)
				.filter(Objects::nonNull).count();
		
		if (nonNullValues != size) {
			throw new InvariantException("size " + size + 
               " does not match value count of " + nonNullValues);
		}
	}
}
