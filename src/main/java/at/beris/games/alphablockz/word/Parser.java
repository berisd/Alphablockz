/*
 * Copyright (C) 2015 Bernd Riedl <bernd.riedl@gmail.com> - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.gui.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Parser {
    private static class ParseContext {
        final Letter[][] letterMatrix;
        final List<String> wordList;
        final List<WordMatch> wordMatches;
        Integer minLengthWord;
        Integer maxLengthWord;

        public ParseContext(Letter[][] letterMatrix, List<String> wordList) {
            this.letterMatrix = letterMatrix;
            this.wordList = wordList;
            wordMatches = new ArrayList<WordMatch>();
        }

        public Letter[][] getLetterMatrix() {
            return letterMatrix;
        }

        public List<String> getWordList() {
            return wordList;
        }

        public List<WordMatch> getWordMatches() {
            return wordMatches;
        }

        public int getLettersPerRow() {
            return letterMatrix.length;
        }

        public int getLettersPerColumn() {
            return letterMatrix[0].length;
        }

        public int getWordMinLength() {
            if (minLengthWord == null) {
                minLengthWord = 9999;
                for (String word : wordList) {
                    if (word.length() < minLengthWord)
                        minLengthWord = word.length();
                }
            }
            return minLengthWord;
        }

        public int getWordMaxLength() {
            if (maxLengthWord == null) {
                maxLengthWord = 0;
                for (String word : wordList) {
                    if (word.length() > maxLengthWord)
                        maxLengthWord = word.length();
                }
            }
            return maxLengthWord;
        }
    }

    private Parser() {
    }

    public static WordMatch findLongestWordMatch(Letter[][] letterMatrix, List<String> wordList) {
        List<WordMatch> wordMatches = parse(letterMatrix, wordList);
        Collections.sort(wordMatches, new WordMatchLengthComparator());
        return wordMatches.size() > 0 ? wordMatches.get(0) : null;
    }

    public static List<WordMatch> parse(Letter[][] letterMatrix, List<String> wordList) {
        ParseContext parseContext = new ParseContext(letterMatrix, wordList);
        findWordsInRows(parseContext);
        findWordsInColumns(parseContext);
        return parseContext.getWordMatches();
    }

    private static void findWordsInRows(ParseContext parseContext) {
        for (int rowIndex = 0; rowIndex < parseContext.getLettersPerColumn(); rowIndex++) {
            parseWordsInRowLeftToRight(parseContext, rowIndex);
            parseWordsInRowRightToLeft(parseContext, rowIndex);
        }
    }

    private static void findWordsInColumns(ParseContext parseContext) {
        for (int colIndex = 0; colIndex < parseContext.getLettersPerRow(); colIndex++) {
            parseWordsInColumnLeftToRight(parseContext, colIndex);
            parseWordsInColumnRightToLeft(parseContext, colIndex);
        }
    }

    private static void parseLetterSequenceLeftToRight(ParseContext parseContext, Letter[] letters, Word letterSequence, Word.Alignment alignment, int columnIndex, int rowIndex, int wordLength) {
        int wordColumnIndex = columnIndex;
        int wordRowIndex = rowIndex;

        for (int index = (alignment == Word.Alignment.HORIZONTAL ? columnIndex : rowIndex); index < (alignment == Word.Alignment.HORIZONTAL ? columnIndex : rowIndex) + wordLength; index++) {
            Letter letter = letters[index];
            Letter previousLetter = ((index > 0) ? letters[index - 1] : null);
            boolean processLetter = true;

            if (letter != null && letterSequence.length() == 0) {
                if (alignment == Word.Alignment.HORIZONTAL)
                    wordColumnIndex = index;
                else
                    wordRowIndex = index;
            }

            if (letter != null && previousLetter != null && letterSequence.length() > 0) {
                if (letter.getColor() != previousLetter.getColor())
                    processLetter = false;
            }

            if (processLetter)
                processLetterSequence(parseContext, letter, letterSequence, alignment, (alignment == Word.Alignment.HORIZONTAL ? Word.Direction.LEFT_TO_RIGHT : Word.Direction.TOP_TO_BOTTOM), wordColumnIndex, wordRowIndex);
        }

        addWordMatchIfExistsInDictionary(parseContext, letterSequence, wordColumnIndex, wordRowIndex, alignment, (alignment == Word.Alignment.HORIZONTAL ? Word.Direction.LEFT_TO_RIGHT : Word.Direction.TOP_TO_BOTTOM));
    }

    private static void parseLetterSequenceRightToLeft(ParseContext parseContext, Letter[] letters, Word letterSequence, Word.Alignment alignment, int columnIndex, int rowIndex, int wordLength) {
        int wordColumnIndex = columnIndex;
        int wordRowIndex = rowIndex;

        for (int index = (alignment == Word.Alignment.HORIZONTAL ? columnIndex : rowIndex); index > (alignment == Word.Alignment.HORIZONTAL ? columnIndex : rowIndex) - wordLength; index--) {
            Letter letter = letters[index];
            Letter previousLetter = ((index < columnIndex) ? letters[index + 1] : null);
            boolean processLetter = true;

            if (letter != null && letterSequence.length() == 0) {
                if (alignment == Word.Alignment.HORIZONTAL)
                    wordColumnIndex = index;
                else
                    wordRowIndex = index;
            }

            if (letter != null && previousLetter != null) {
                if (letter.getColor() != previousLetter.getColor())
                    processLetter = false;
            }

            if (processLetter)
                processLetterSequence(parseContext, letter, letterSequence, alignment, (alignment == Word.Alignment.HORIZONTAL ? Word.Direction.RIGHT_TO_LEFT : Word.Direction.BOTTOM_TO_TOP), wordColumnIndex, wordRowIndex);
        }

        addWordMatchIfExistsInDictionary(parseContext, letterSequence, wordColumnIndex, wordRowIndex, alignment, (alignment == Word.Alignment.HORIZONTAL ? Word.Direction.RIGHT_TO_LEFT : Word.Direction.BOTTOM_TO_TOP));
    }

    private static void addWordMatchIfExistsInDictionary(ParseContext parseContext, Word letterSequence, int columnIndex, int rowIndex, Word.Alignment alignment, Word.Direction direction) {
        if (letterSequence.length() >= parseContext.getWordMinLength() && letterSequence.length() <= parseContext.getWordMaxLength()) {
            if (parseContext.getWordList().contains(letterSequence.toString())) {
                boolean wordExistsInMatchList = false;
                for (WordMatch wordMatch : parseContext.getWordMatches()) {
                    if (wordMatch.getWord().equals(letterSequence)) {
                        wordExistsInMatchList = true;
                        break;
                    }
                }
                if (!wordExistsInMatchList) {
                    WordMatch wordMatch = new WordMatch();
                    wordMatch.setLocation(new Point(columnIndex, rowIndex));
                    wordMatch.setAlignment(alignment);
                    wordMatch.setDirection(direction);
                    wordMatch.setWord(new Word(letterSequence));
                    parseContext.getWordMatches().add(wordMatch);
                }
            }
        }
    }

    private static void parseWordsInRowLeftToRight(ParseContext parseContext, int rowIndex) {
        Word letterSequence = new Word();


        for (int colIndex = 0; colIndex < parseContext.getLettersPerRow(); colIndex++) {
            for (int wordLength = parseContext.getWordMinLength(); wordLength <= parseContext.getWordMaxLength(); wordLength++) {
                if (colIndex + wordLength <= parseContext.getLettersPerRow()) {
                    parseLetterSequenceLeftToRight(parseContext, parseContext.getLetterMatrix()[rowIndex], letterSequence, Word.Alignment.HORIZONTAL, colIndex, rowIndex, wordLength);
                    letterSequence.clear();
                }
            }
        }
    }

    private static void parseWordsInRowRightToLeft(ParseContext parseContext, int rowIndex) {
        Word letterSequence = new Word();
        int lettersPerRow = parseContext.getLettersPerRow();

        for (int colIndex = lettersPerRow - 1; colIndex >= 0; colIndex--) {
            for (int wordLength = parseContext.getWordMinLength(); wordLength <= parseContext.getWordMaxLength(); wordLength++) {
                if (colIndex >= wordLength - 1) {
                    parseLetterSequenceRightToLeft(parseContext, parseContext.getLetterMatrix()[rowIndex], letterSequence, Word.Alignment.HORIZONTAL, colIndex, rowIndex, wordLength);
                    letterSequence.clear();
                }
            }
        }
    }

    private static void processLetterSequence(ParseContext parseContext, Letter letter, Word letterSequence, Word.Alignment alignment, Word.Direction direction, int columnIndex, int rowIndex) {
        boolean appendingLetterPossible;

        appendingLetterPossible = (!(letter == null && letterSequence.length() > 0));

        if (!appendingLetterPossible) {
            addWordMatchIfExistsInDictionary(parseContext, letterSequence, columnIndex, rowIndex, alignment, direction);
            letterSequence.clear();
        }

        if (letter != null) {
            letterSequence.add(letter);

        }
    }


    private static void parseWordsInColumnLeftToRight(ParseContext parseContext, int colIndex) {
        Word letterSequence = new Word();
        int lettersPerColumn = parseContext.getLettersPerColumn();

        Letter[] letterArray = new Letter[lettersPerColumn];

        for (int rowIndex = 0; rowIndex < lettersPerColumn; rowIndex++) {
            letterArray[rowIndex] = parseContext.getLetterMatrix()[rowIndex][colIndex];
        }

        for (int rowIndex = 0; rowIndex < lettersPerColumn; rowIndex++) {
            for (int wordLength = parseContext.getWordMinLength(); wordLength <= parseContext.getWordMaxLength(); wordLength++) {
                if (rowIndex + wordLength <= lettersPerColumn) {
                    parseLetterSequenceLeftToRight(parseContext, letterArray, letterSequence, Word.Alignment.VERTICAL, colIndex, rowIndex, wordLength);
                    letterSequence.clear();
                }
            }
        }
    }

    private static void parseWordsInColumnRightToLeft(ParseContext parseContext, int colIndex) {
        Word letterSequence = new Word();

        Letter[] letterArray = new Letter[parseContext.getLettersPerColumn()];

        for (int rowIndex = parseContext.getLettersPerColumn() - 1; rowIndex >= 0; rowIndex--) {
            letterArray[rowIndex] = parseContext.getLetterMatrix()[rowIndex][colIndex];
        }

        for (int rowIndex = parseContext.getLettersPerColumn() - 1; rowIndex >= 0; rowIndex--) {
            for (int wordLength = parseContext.getWordMinLength(); wordLength <= parseContext.getWordMaxLength(); wordLength++) {
                if (rowIndex >= wordLength - 1) {
                    parseLetterSequenceRightToLeft(parseContext, letterArray, letterSequence, Word.Alignment.VERTICAL, colIndex, rowIndex, wordLength);
                    letterSequence.clear();
                }
            }
        }
    }
}
