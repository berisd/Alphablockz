/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.Board;
import at.beris.games.alphablockz.gui.WordList;
import at.beris.games.alphablockz.word.*;
import at.beris.games.alphablockz.word.Dictionary;

import java.util.*;

public class GameLogic {
    public final static int LEVEL_MAX_PLAYING_TIME_IN_SEC = 300;

    private final Dictionary dictionary;
    private final Board board;
    private final WordList wordList;

    private final int[] scorePerWordLength;

    private long startTimeMs;
    private int level;
    private int score;
    private int highScore;

    private boolean isLevelComplete;
    private boolean isGameOver;
    private boolean isPlaying;

    private int numberOfWordsFound;
    private WordMatch lastWordMatch;

    private final List<Integer> wordLengthsRequiredForLevel;
    private TreeMap<Integer, Character> letterFrequencyRangeMap;

    public GameLogic() {
        dictionary = Dictionary.getInstance();
        board = new Board();
        wordList = new WordList();
        scorePerWordLength = new int[]{10, 25, 50, 100, 250, 500, 1000, 2500, 5000, 10000, 25000, 50000, 100000};
        wordLengthsRequiredForLevel = new ArrayList<Integer>();
        letterFrequencyRangeMap = new TreeMap<Integer, Character>();
    }

    public Board getBoard() {
        return board;
    }

    public WordList getWordList() {
        return wordList;
    }

    int getPlayingTimeInSec() {
        return (int) ((System.currentTimeMillis() - startTimeMs) / 1000);
    }

    int getPercentageLevelCompleted() {
        return (int) (((double) numberOfWordsFound / wordsRequiredForLevelCompleted()) * 100);
    }

    public boolean isLevelComplete() {
        return isLevelComplete;
    }

    int wordsRequiredForLevelCompleted() {
        return 5 + (level - 1);
    }

    public int getHighScore() {
        return highScore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfWordsFound() {
        return numberOfWordsFound;
    }

    public void setNumberOfWordsFound(int numberOfWordsFound) {
        this.numberOfWordsFound = numberOfWordsFound;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void startGame() {
        score = 0;
        level = 0;
        isPlaying = true;
        isGameOver = false;
    }

    public void gameOver() {
        isGameOver = true;
        if (score > highScore)
            highScore = score;
    }

    public void exitGame() {
        isPlaying = false;

        if (score > highScore)
            highScore = score;
    }

    public void startLevel() {
        level++;
        numberOfWordsFound = 0;
        isLevelComplete = false;

        board.clear();
        wordList.clear();

        startTimeMs = System.currentTimeMillis();
        calculateWordLengthsForLevel();
        fillWordList();
        calculateLetterFrequencies();
    }

    private void calculateLetterFrequencies() {
        Map<Character, Integer> frequencyMap = getLetterFrequencyMap();
        letterFrequencyRangeMap.clear();
        Integer frequencyRangeEnd = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            Integer frequency = entry.getValue();
            frequencyRangeEnd+=frequency;
            letterFrequencyRangeMap.put(frequencyRangeEnd, entry.getKey());
        }
    }

    private Map<Character, Integer> getLetterFrequencyMap() {
        Map<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
        for (String word : wordList.asStringList()) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                if (frequencyMap.get(c) == null) {
                    frequencyMap.put(c, 0);
                }
                frequencyMap.put(c, frequencyMap.get(c)+1);
            }
        }
        return frequencyMap;
    }

    private void fillWordList() {
        while (wordList.size() < 5) {
            addNewWordToWordList();
        }
    }

    public void levelCompleted() {
        isLevelComplete = true;
        score += (LEVEL_MAX_PLAYING_TIME_IN_SEC - ((System.currentTimeMillis() - startTimeMs) / 1000));
    }


    int calculateCountWordLengths() {
        int countWordLengths = 5 + 2 * ((int) (level - 1) / 4);

        if ((level % 4 == 0) || (level % 4 == 3))
            countWordLengths++;

        if (level % 4 == 0)
            countWordLengths++;

        return countWordLengths;
    }

    int calculateMaxWordLength() {
        return Dictionary.WORD_MIN_LENGTH + ((level + 2) / 4);
    }

    void calculateWordLengthsForLevel() {
        wordLengthsRequiredForLevel.clear();

        int maxWordLength = calculateMaxWordLength();
        int countWordLengths = calculateCountWordLengths();

        int countMaxWordLength = 0;
        int countMaxWordLengthMinusOne = 0;

        if (level % 4 == 1) {
            countMaxWordLength = countWordLengths;
        } else if (level % 4 == 2) {
            countMaxWordLength = countWordLengths / 2;
            countMaxWordLengthMinusOne = (countWordLengths / 2) + (countWordLengths % 2);
        } else if (level % 4 == 3) {
            countMaxWordLength = countWordLengths / 2;
            countMaxWordLengthMinusOne = countWordLengths / 2;
        } else if (level % 4 == 0) {
            countMaxWordLength = (countWordLengths / 2) + (countWordLengths % 2);
            countMaxWordLengthMinusOne = countWordLengths / 2;
        }

        for (int i = 0; i < countMaxWordLengthMinusOne; i++) {
            wordLengthsRequiredForLevel.add(maxWordLength - 1);
        }

        for (int i = 0; i < countMaxWordLength; i++) {
            wordLengthsRequiredForLevel.add(maxWordLength);
        }
    }

    List<Integer> getWordLengthsRequiredForLevel() {
        return wordLengthsRequiredForLevel;
    }

    public int calculateScore(Word word) {
        int index = word.length() - Dictionary.WORD_MIN_LENGTH;

        if (index >= scorePerWordLength.length)
            index = scorePerWordLength.length - 1;

        return scorePerWordLength[index];
    }

    public void wordFound() {
        Word word = lastWordMatch.getWord();
        numberOfWordsFound++;
        score += calculateScore(word);
        wordList.remove(word.toString());
        board.removeWord(lastWordMatch);
        lastWordMatch = null;
    }

    public void addNewWordToWordList() {
        Random rand = new Random();

        Integer randomWordLength = wordLengthsRequiredForLevel.get(rand.nextInt(wordLengthsRequiredForLevel.size()));
        String randomWord;
        do {
            randomWord = dictionary.getRandomWord(randomWordLength);
        }
        while (wordList.contains(randomWord));

        wordList.push(randomWord, LetterColor.getRandomForeColor().getColor());
        calculateLetterFrequencies();
    }

    public boolean wordListContainsWordFound() {
        boolean wordFoundInList = false;
        if (lastWordMatch != null) {
            Word word = lastWordMatch.getWord();
            wordFoundInList = wordList.contains(word.toString());
        }
        return wordFoundInList;
    }

    public void findLongestWordOnBoard() {
        lastWordMatch = Parser.findLongestWordMatch(board.getLetterMatrix(), wordList.asStringList());
    }

    public void clearLastWordMatch() {
        lastWordMatch = null;
    }

    public WordMatch getLastWordMatch() {
        return lastWordMatch;
    }

    public Letter createRandomLetter() {
        Random rand = new Random();
        int frequencyRangeStep = 1 + rand.nextInt(letterFrequencyRangeMap.lastKey());
        Map.Entry<Integer, Character> frequencyEntry = letterFrequencyRangeMap.floorEntry(frequencyRangeStep);
        if (frequencyEntry == null) {
            frequencyEntry = letterFrequencyRangeMap.higherEntry(frequencyRangeStep);
        }

        Letter letter = new Letter(frequencyEntry.getValue());
        letter.setColor(LetterColor.getRandomForeColor());
        return letter;
    }
}
