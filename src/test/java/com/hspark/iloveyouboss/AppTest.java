package com.hspark.iloveyouboss;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void answerArithmeticMeanOfTwoNumbers() throws Exception {
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);
        int actualResult = collection.arithmeticMean();
        assertEquals(actualResult, 6);
    }
}
