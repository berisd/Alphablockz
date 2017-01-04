/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ParserTest {
    Letter[][] letterMatrix;
    List<String> wordList;

    enum BoardType {COLUMN, ROW}

    ;

    private final static int MATRIX_WIDTH_IN_LETTERS = 20;
    private final static int MATRIX_HEIGHT_IN_LETTERS = 20;

    @Before
    public void setup() {

        letterMatrix = new Letter[MATRIX_WIDTH_IN_LETTERS][MATRIX_HEIGHT_IN_LETTERS];
        wordList = createWordList();
    }

    @Test
    public void findWordReverseInRowTopLeft() {
        Word wordJill = new Word("llij");
        addWordToMatrixRow(0, 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(0 + wordJill.length(), 0, 0, noiseLetters);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.RIGHT_TO_LEFT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInRowTopRight() {
        Word wordJill = new Word("llij");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length(), 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.RIGHT_TO_LEFT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInRowOnBottomLeft() {
        Word wordJill = new Word("llij");
        addWordToMatrixRow(0, MATRIX_HEIGHT_IN_LETTERS - 1, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(wordJill.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.RIGHT_TO_LEFT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInRowOnBottomRight() {
        Word wordJill = new Word("llij");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length() - noiseLetters.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.RIGHT_TO_LEFT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInColumnTopLeft() {
        Word wordJillReverse = new Word("llij");
        addWordToMatrixColumn(0, 0, 0, wordJillReverse);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(0, 0 + wordJillReverse.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.BOTTOM_TO_TOP, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInColumnOnTopRight() {
        Word wordJill = new Word("llij");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, 0 + wordJill.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.BOTTOM_TO_TOP, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInColumnOnBottomLeft() {
        Word wordJill = new Word("llij");
        addWordToMatrixColumn(0, MATRIX_HEIGHT_IN_LETTERS - wordJill.length(), 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(0, MATRIX_HEIGHT_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.BOTTOM_TO_TOP, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordReverseInColumnOnBottomRight() {
        Word wordJill = new Word("llij");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, MATRIX_HEIGHT_IN_LETTERS - wordJill.length(), 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, MATRIX_HEIGHT_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.BOTTOM_TO_TOP, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInRowOnTopLeft() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(0, 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(0 + wordJill.length(), 0, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.LEFT_TO_RIGHT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInRowOnTopRight() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length(), 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.LEFT_TO_RIGHT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInRowOnBottomLeft() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(0, MATRIX_HEIGHT_IN_LETTERS - 1, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(0 + wordJill.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.LEFT_TO_RIGHT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }


    @Test
    public void findWordInRowOnBottomRight() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixRow(MATRIX_WIDTH_IN_LETTERS - wordJill.length() - noiseLetters.length(), MATRIX_HEIGHT_IN_LETTERS - 1, 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.LEFT_TO_RIGHT, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInColumnOnTopLeft() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(0, 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(0, 0 + wordJill.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.TOP_TO_BOTTOM, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInColumnOnTopRight() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, 0, 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, 0 + wordJill.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.TOP_TO_BOTTOM, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInColumnOnBottomLeft() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(0, MATRIX_HEIGHT_IN_LETTERS - wordJill.length(), 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(0, MATRIX_HEIGHT_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.TOP_TO_BOTTOM, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInColumnOnBottomRight() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, MATRIX_HEIGHT_IN_LETTERS - wordJill.length(), 0, wordJill);

        Word noiseLetters = new Word("xjte");
        addWordToMatrixColumn(MATRIX_WIDTH_IN_LETTERS - 1, MATRIX_HEIGHT_IN_LETTERS - wordJill.length() - noiseLetters.length(), 0, noiseLetters);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.TOP_TO_BOTTOM, wordMatch.getDirection());
        assertEquals("jill", wordMatch.getWord().toString());
    }

    @Test
    public void findLongestWordInRow() {
        Word wordTea = new Word("tea");
        Word wordParty = new Word("party");
        addWordToMatrixRow(1, 0, 2, wordTea);
        addWordToMatrixRow(8, 0, 2, wordParty);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("party", wordMatch.getWord().toString());
    }

    @Test
    public void findLongestWordInColumn() {
        Word wordTea = new Word("tea");
        Word wordParty = new Word("party");
        addWordToMatrixColumn(0, 1, 2, wordTea);
        addWordToMatrixColumn(0, 8, 2, wordParty);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("party", wordMatch.getWord().toString());
    }

    @Test
    public void findLongestWordInRows() {
        Word wordTea = new Word("tea");
        Word wordParty = new Word("party");
        Word wordChampion = new Word("champion");

        addWordToMatrixRow(1, 8, 0, wordTea);
        addWordToMatrixRow(8, 8, 0, wordParty);
        addWordToMatrixRow(3, 10, 0, wordChampion);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("champion", wordMatch.getWord().toString());
    }

    @Test
    public void findLongestWordInColumns() {
        Word wordTea = new Word("tea");
        Word wordParty = new Word("party");
        Word wordChampion = new Word("champion");

        addWordToMatrixColumn(8, 1, 0, wordTea);
        addWordToMatrixColumn(8, 8, 0, wordParty);
        addWordToMatrixColumn(10, 3, 0, wordChampion);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("champion", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInRowWithLongestMatch() {
        Word wordJillion = new Word("jillion");
        addWordToMatrixRow(0, 0, 0, wordJillion);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("jillion", wordMatch.getWord().toString());
    }

    @Test
    public void findWordInColumnWithLongestMatch() {
        Word wordJillion = new Word("jillion");
        addWordToMatrixColumn(0, 0, 0, wordJillion);

        WordMatch wordMatch = Parser.findLongestWordMatch(letterMatrix, wordList);
        assertEquals("jillion", wordMatch.getWord().toString());
    }

    @Test
    public void dontfindWordInRowWhenDistanceBetweenLettersGreaterZero() {
        Word wordJil = new Word("jil");
        addWordToMatrixRow(0, 0, 0, wordJil);

        Letter letter = new Letter('l');
        wordJil.add(letter);
        letterMatrix[0][4] = letter;

        assertSame(null, Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void dontfindWordInColumnWhenDistanceBetweenLettersGreaterZero() {
        Word wordJil = new Word("jil");
        addWordToMatrixColumn(0, 0, 0, wordJil);

        Letter letter = new Letter('l');
        wordJil.add(letter);
        letterMatrix[4][0] = letter;

        assertEquals(null, Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void findsTwoWordsIfWordIsAlsoInDictionaryReversed() {
        Word wordMerci = new Word("merci");
        addWordToMatrixRow(0, 0, 0, wordMerci);

        List<WordMatch> wordMatchList = Parser.parse(letterMatrix, wordList);
        assertEquals(2, wordMatchList.size());

        List<Word> resultList = new ArrayList<Word>();
        resultList.add(wordMatchList.get(0).getWord());
        resultList.add(wordMatchList.get(1).getWord());

        assertTrue(resultList.contains(new Word("merci")));
        assertTrue(resultList.contains(new Word("icrem")));
    }

    @Test
    public void dontfindWordInRowWhenLettersHaveADifferentColor() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(0, 0, 0, wordJill);
        wordJill.get(2).setColor(LetterColor.BLUE);
        assertNull(Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void dontfindWordsInColumnWhenLettersHaveADifferentColor() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(0, 0, 0, wordJill);
        wordJill.get(2).setColor(LetterColor.BLUE);
        assertNull(Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void wordNotFoundOnBoard() {
        Word wordXtjl = new Word("xtjl");
        addWordToMatrixRow(1, 1, 1, wordXtjl);
        assertNull(Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void findNothingOnEmptyBoard() {
        assertNull(Parser.findLongestWordMatch(letterMatrix, wordList));
    }

    @Test
    public void checkLocationOfHorizontalLeftToRightWord() {
        Word wordJill = new Word("jill");
        addWordToMatrixRow(5, 10, 0, wordJill);


        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.LEFT_TO_RIGHT, wordMatch.getDirection());
        assertEquals(5, wordMatch.getLocation().getX());
        assertEquals(10, wordMatch.getLocation().getY());
    }

    @Test
    public void checkLocationOfHorizontalRightToLeftWord() {
        Word wordJill = new Word("llij");
        addWordToMatrixRow(5, 10, 0, wordJill);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.HORIZONTAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.RIGHT_TO_LEFT, wordMatch.getDirection());
        assertEquals(5 + wordJill.length() - 1, wordMatch.getLocation().getX());
        assertEquals(10, wordMatch.getLocation().getY());
    }

    @Test
    public void checkLocationOfVerticalLeftToRightWord() {
        Word wordJill = new Word("jill");
        addWordToMatrixColumn(5, 10, 0, wordJill);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.TOP_TO_BOTTOM, wordMatch.getDirection());
        assertEquals(5, wordMatch.getLocation().getX());
        assertEquals(10, wordMatch.getLocation().getY());
    }

    @Test
    public void checkLocationOfVerticalRightToLeftWord() {
        Word wordJill = new Word("llij");
        addWordToMatrixColumn(5, 10, 0, wordJill);

        WordMatch wordMatch = parseAndGetFirstWordMatch();
        assertEquals(Word.Alignment.VERTICAL, wordMatch.getAlignment());
        assertEquals(Word.Direction.BOTTOM_TO_TOP, wordMatch.getDirection());
        assertEquals(5, wordMatch.getLocation().getX());
        assertEquals(13, wordMatch.getLocation().getY());
    }

    private void addWordToMatrixRow(int columnIndex, int rowIndex, int distanceBetweenLetters, Word word) {
        addWordToMatrix(columnIndex, rowIndex, distanceBetweenLetters, word, BoardType.ROW);
    }

    private void addWordToMatrixColumn(int columnIndex, int rowIndex, int distanceBetweenLetters, Word word) {
        addWordToMatrix(columnIndex, rowIndex, distanceBetweenLetters, word, BoardType.COLUMN);
    }

    private void addWordToMatrix(int columnIndex, int rowIndex, int distanceBetweenLetters, Word word, BoardType type) {
        int x = columnIndex + (type == BoardType.ROW ? distanceBetweenLetters : 0);
        int y = rowIndex + (type == BoardType.COLUMN ? distanceBetweenLetters : 0);

        for (int i = 0; i < word.length(); i++) {
            word.get(i).setColor(LetterColor.RED);

            if (type == BoardType.ROW) {
                letterMatrix[rowIndex][columnIndex + i] = word.get(i);
                x++;
            } else {
                letterMatrix[rowIndex + i][columnIndex] = word.get(i);
                y++;
            }
        }
    }

    private WordMatch parseAndGetFirstWordMatch() {
        List<WordMatch> wordMatches = Parser.parse(letterMatrix, wordList);
        return (wordMatches.size() > 0) ? wordMatches.get(0) : new WordMatch();
    }

    private List<String> createWordList() {
        List<String> wordList = new ArrayList<String>();
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int j = 0; j <= 20; j++) {
            for (int i = 0; i < Dictionary.WORD_MAX_LENGTH; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            wordList.add(sb.toString());
            sb.setLength(0);
        }
        wordList.add("jill");
        wordList.add("jillion");
        wordList.add("tea");
        wordList.add("party");
        wordList.add("champion");
        wordList.add("merci");
        wordList.add("icrem");
        return wordList;
    }

    private void printMatrix() {
        StringBuffer sb = new StringBuffer();
        for (int y = 0; y < MATRIX_HEIGHT_IN_LETTERS; y++) {
            if (y > 0)
                sb.append(System.lineSeparator());
            for (int x = 0; x < MATRIX_WIDTH_IN_LETTERS; x++) {
                if (letterMatrix[y][x] != null)
                    sb.append(letterMatrix[y][x].getCharacter());
                else
                    sb.append('.');
            }
        }
        System.out.println(sb.toString());
    }
}