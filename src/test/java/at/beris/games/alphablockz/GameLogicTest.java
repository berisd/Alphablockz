/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.word.Word;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GameLogicTest {

    GameLogic gameLogic;

    @Before
    public void setup() {
        gameLogic = new GameLogic();
    }

    @Test
    public void calculateSmallestScore() {
        assertEquals(10, gameLogic.calculateScore(new Word("aaa")));
    }

    @Test
    public void calculateBiggestScore() {
        assertEquals(100000, gameLogic.calculateScore(new Word("aaaaaaaaaaaaaaa")));
    }

    @Test
    public void checkCountWordLengthsForLevels() {
        Map<Integer, Integer> mapLevelToCount = new HashMap<Integer, Integer>();
        mapLevelToCount.put(1, 5);
        mapLevelToCount.put(2, 5);
        mapLevelToCount.put(3, 6);
        mapLevelToCount.put(4, 7);
        mapLevelToCount.put(5, 7);
        mapLevelToCount.put(6, 7);
        mapLevelToCount.put(7, 8);
        mapLevelToCount.put(8, 9);
        mapLevelToCount.put(9, 9);
        mapLevelToCount.put(10, 9);
        mapLevelToCount.put(11, 10);
        mapLevelToCount.put(12, 11);

        for (Map.Entry<Integer, Integer> entry : mapLevelToCount.entrySet()) {
            int level = entry.getKey();
            int countWordLengths = entry.getValue();

            gameLogic.setLevel(level);
            assertEquals("check countWordLengths for Level " + level, countWordLengths, gameLogic.calculateCountWordLengths());
        }
    }

    @Test
    public void checkMaxWordLengthForLevels() {
        Map<Integer, Integer> mapLevelToLength = new HashMap<Integer, Integer>();
        mapLevelToLength.put(1, 3);
        mapLevelToLength.put(2, 4);
        mapLevelToLength.put(3, 4);
        mapLevelToLength.put(4, 4);
        mapLevelToLength.put(5, 4);
        mapLevelToLength.put(6, 5);
        mapLevelToLength.put(7, 5);
        mapLevelToLength.put(8, 5);
        mapLevelToLength.put(9, 5);
        mapLevelToLength.put(10, 6);
        mapLevelToLength.put(11, 6);
        mapLevelToLength.put(12, 6);

        for (Map.Entry<Integer, Integer> entry : mapLevelToLength.entrySet()) {
            int level = entry.getKey();
            int maxWordLength = entry.getValue();

            gameLogic.setLevel(level);
            assertEquals("check maxWordLength for Level " + level, maxWordLength, gameLogic.calculateMaxWordLength());
        }
    }

    @Test
    public void wordLengthsForLevel1() { // start  wordCount = 5 + 2 * (level -1) / 4 ;       maxWordLength = 2 + 1
        Map wordLengthsCount = getWordLengthsForLevel(1);
        assertEquals(1, wordLengthsCount.size());
        assertEquals(5, wordLengthsCount.get(3));
    }

    @Test
    public void wordLengthsForLevel2() { // asc   countMaxLen = (wordCount/2) countMaxLen-1 = (wordCount/2) + (wordCount%2)
        Map wordLengthsCount = getWordLengthsForLevel(2);
        assertEquals(2, wordLengthsCount.size());
        assertEquals(3, wordLengthsCount.get(3));
        assertEquals(2, wordLengthsCount.get(4));
    }

    @Test
    public void wordLengthsForLevel3() { // middle wordCount++ ,  countMaxLen = (wordCount/2) countMaxLen-1 = (wordCount/2)
        Map wordLengthsCount = getWordLengthsForLevel(3);
        assertEquals(2, wordLengthsCount.size());
        assertEquals(3, wordLengthsCount.get(3));
        assertEquals(3, wordLengthsCount.get(4));
    }

    @Test
    public void wordLengthsForLevel4() { // desc wordCount++ , countMaxLen = (wordCount/2)  + (wordCount%2) ,  countMaxLen-1 = (wordCount/2)
        Map wordLengthsCount = getWordLengthsForLevel(4); // summiere counts zu maxWordLength
        assertEquals(2, wordLengthsCount.size());
        assertEquals(3, wordLengthsCount.get(3));
        assertEquals(4, wordLengthsCount.get(4));
    }

    @Test
    public void wordLengthsForLevel5() { // start wordCount = 9
        Map wordLengthsCount = getWordLengthsForLevel(5); // wordCount +1
        assertEquals(1, wordLengthsCount.size());
        assertEquals(7, wordLengthsCount.get(4));
    }

    @Test
    public void wordLengthsForLevel6() { // asc
        Map wordLengthsCount = getWordLengthsForLevel(6);
        assertEquals(2, wordLengthsCount.size());
        assertEquals(4, wordLengthsCount.get(4));
        assertEquals(3, wordLengthsCount.get(5));

    }

    @Test
    public void wordLengthsForLevel7() { // middle
        Map wordLengthsCount = getWordLengthsForLevel(7);
        assertEquals(2, wordLengthsCount.size());
        assertEquals(4, wordLengthsCount.get(4));
        assertEquals(4, wordLengthsCount.get(5));

    }

    @Test
    public void wordLengthsForLevel8() { // desc
        Map wordLengthsCount = getWordLengthsForLevel(8);
        assertEquals(2, wordLengthsCount.size());
        assertEquals(4, wordLengthsCount.get(4));
        assertEquals(5, wordLengthsCount.get(5));
    }

    @Test
    public void wordLengthsForLevel9() { // start
        Map wordLengthsCount = getWordLengthsForLevel(9);
        assertEquals(1, wordLengthsCount.size());
        assertEquals(9, wordLengthsCount.get(5));
    }

    @Test
    public void wordLengthsForLevel10() { // asc
        Map wordLengthsCount = getWordLengthsForLevel(10); // wordCount+2
        assertEquals(2, wordLengthsCount.size());
        assertEquals(5, wordLengthsCount.get(5));
        assertEquals(4, wordLengthsCount.get(6));
    }

    @Test
    public void wordLengthsForLevel11() { // middle
        Map wordLengthsCount = getWordLengthsForLevel(11); // wordCount+2
        assertEquals(2, wordLengthsCount.size());
        assertEquals(5, wordLengthsCount.get(5));
        assertEquals(5, wordLengthsCount.get(6));
    }

    @Test
    public void wordLengthsForLevel12() { // desc
        Map wordLengthsCount = getWordLengthsForLevel(12); // wordCount+2
        assertEquals(2, wordLengthsCount.size());
        assertEquals(5, wordLengthsCount.get(5));
        assertEquals(6, wordLengthsCount.get(6));
    }


    private Map getWordLengthsForLevel(int level) {
        gameLogic.setLevel(level);
        gameLogic.calculateWordLengthsForLevel();

        return getWordLengthsCount(gameLogic.getWordLengthsRequiredForLevel());
    }

    @Test
    public void percentageLevelCompleted() {
        gameLogic.setNumberOfWordsFound(1);
        gameLogic.setLevel(1);
        assertEquals(20, gameLogic.getPercentageLevelCompleted());
    }

    private Map<Integer, Integer> getWordLengthsCount(List<Integer> listWordLength) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Integer wordLength : listWordLength) {
            Integer wordLengthCount = map.get(wordLength);

            if (wordLengthCount == null)
                wordLengthCount = 0;
            wordLengthCount++;

            map.put(wordLength, wordLengthCount);
        }

        return map;
    }
}
