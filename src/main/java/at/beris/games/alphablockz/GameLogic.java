/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.Board;
import at.beris.games.alphablockz.gui.WordList;
import at.beris.games.alphablockz.word.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public GameLogic() {
        dictionary = Dictionary.getInstance();
        board = new Board();
        wordList = new WordList();
        scorePerWordLength = new int[]{10, 25, 50, 100, 250, 500, 1000, 2500, 5000, 10000, 25000, 50000, 100000};
        wordLengthsRequiredForLevel = new ArrayList<Integer>();
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
        String randomWord = wordList.getRandomWord();
        char randomChar = randomWord.charAt(rand.nextInt(randomWord.length()));

        Letter letter = new Letter(randomChar);
        letter.setColor(LetterColor.getRandomForeColor());

        return letter;
    }
}
