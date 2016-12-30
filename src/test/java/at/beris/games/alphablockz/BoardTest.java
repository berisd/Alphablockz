/* Copyright (C) Bernd Riedl - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Bernd Riedl <bernd.riedl@gmail.com>, August 2015
 */
package at.beris.games.alphablockz;

import at.beris.games.alphablockz.gui.Board;
import at.beris.games.alphablockz.gui.Point;
import at.beris.games.alphablockz.word.Parser;
import at.beris.games.alphablockz.word.WordMatch;
import at.beris.games.alphablockz.word.Dictionary;
import at.beris.games.alphablockz.word.Letter;
import at.beris.games.alphablockz.word.LetterColor;
import at.beris.games.alphablockz.word.Word;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class BoardTest {

    Board board;
    List<String> wordList;

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;

    @Before
    public void setup() {
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        wordList = createWordList();
    }

    @Test
    public void noCollisionBetweenLetters() {
        board.setCurrentLetter(new Letter('a'), 2, 2);
        assertFalse(board.collisionDetected(3, 2));
    }

    @Test
    public void horizontalCollisionBetweenLetters() {
        board.setCurrentLetter(new Letter('a'), 1, 2);
        assertTrue(board.collisionDetected(1, 2));
    }

    @Test
    public void verticalCollisionBetweenLetters() {
        board.setCurrentLetter(new Letter('a'), 2, 1);
        assertTrue(board.collisionDetected(2, 1));
    }

    @Test
    public void removeWordWithLongestMatchFromRow() {
        Word wordJillion = new Word("jillion");
        Word wordJill = new Word("jill");

        createWordOnBoardInRow(1, 5, 0, wordJillion);
        createWordOnBoardInRow(1, 12, 0, wordJill);

        WordMatch longestWordMatch = Parser.findLongestWordMatch(board.getLetterMatrix(), wordList);
        assertEquals("jillion", longestWordMatch.getWord().toString());
        board.removeWord(longestWordMatch);

        longestWordMatch = Parser.findLongestWordMatch(board.getLetterMatrix(), wordList);
        assertEquals("jill", longestWordMatch.getWord().toString());
        board.removeWord(longestWordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordWithLongestMatchFromColumn() {
        Word wordJillion = new Word("jillion");
        Word wordJill = new Word("jill");

        createWordOnBoardColumn(5, 1, 0, wordJillion);
        createWordOnBoardColumn(12, 1, 0, wordJill);

        WordMatch longestWordMatch = Parser.findLongestWordMatch(board.getLetterMatrix(), wordList);
        assertEquals("jillion", longestWordMatch.getWord().toString());
        board.removeWord(longestWordMatch);

        longestWordMatch = Parser.findLongestWordMatch(board.getLetterMatrix(), wordList);
        assertEquals("jill", longestWordMatch.getWord().toString());
        board.removeWord(longestWordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromRowTopLeft() {
        Word wordJill = new Word("jill");
        createWordOnBoardInRow(0, 0, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.HORIZONTAL);
        wordMatch.setDirection(Word.Direction.LEFT_TO_RIGHT);
        wordMatch.setLocation(new Point(0, 0));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromRowTopLRight() {
        Word wordJill = new Word("jill");
        createWordOnBoardInRow(BOARD_WIDTH - wordJill.length(), 0, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.HORIZONTAL);
        wordMatch.setDirection(Word.Direction.LEFT_TO_RIGHT);
        wordMatch.setLocation(new Point(BOARD_WIDTH - wordJill.length(), 0));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromRowBottomLeft() {
        Word wordJill = new Word("jill");
        createWordOnBoardInRow(0, BOARD_HEIGHT - 1, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.HORIZONTAL);
        wordMatch.setDirection(Word.Direction.LEFT_TO_RIGHT);
        wordMatch.setLocation(new Point(0, BOARD_HEIGHT - 1));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromRowBottomRight() {
        Word wordJill = new Word("jill");
        createWordOnBoardInRow(BOARD_WIDTH - wordJill.length(), BOARD_HEIGHT - 1, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.HORIZONTAL);
        wordMatch.setDirection(Word.Direction.LEFT_TO_RIGHT);
        wordMatch.setLocation(new Point(BOARD_WIDTH - wordJill.length(), BOARD_HEIGHT - 1));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromColumnTopLeft() {
        Word wordJill = new Word("jill");
        createWordOnBoardColumn(0, 0, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.VERTICAL);
        wordMatch.setDirection(Word.Direction.TOP_TO_BOTTOM);
        wordMatch.setLocation(new Point(0, 0));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromColumnTopRight() {
        Word wordJill = new Word("jill");
        createWordOnBoardColumn(BOARD_WIDTH - wordJill.length(), 0, 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.VERTICAL);
        wordMatch.setDirection(Word.Direction.TOP_TO_BOTTOM);
        wordMatch.setLocation(new Point(BOARD_WIDTH - wordJill.length(), 0));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromColumnBottomLeft() {
        Word wordJill = new Word("jill");
        createWordOnBoardColumn(0, BOARD_HEIGHT - wordJill.length(), 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.VERTICAL);
        wordMatch.setDirection(Word.Direction.TOP_TO_BOTTOM);
        wordMatch.setLocation(new Point(0, BOARD_HEIGHT - wordJill.length()));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void removeWordFromColumnBottomRight() {
        Word wordJill = new Word("jill");
        createWordOnBoardColumn(BOARD_WIDTH - 1, BOARD_HEIGHT - wordJill.length(), 0, wordJill);

        WordMatch wordMatch = new WordMatch();
        wordMatch.setWord(wordJill);
        wordMatch.setAlignment(Word.Alignment.VERTICAL);
        wordMatch.setDirection(Word.Direction.TOP_TO_BOTTOM);
        wordMatch.setLocation(new Point(BOARD_WIDTH - 1, BOARD_HEIGHT - wordJill.length()));

        board.removeWord(wordMatch);
        assertNull(Parser.findLongestWordMatch(board.getLetterMatrix(), wordList));
    }

    @Test
    public void clear() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_WIDTH; i++) {
            sb.append('a');
        }

        for (int y = 0; y < BOARD_HEIGHT; y++) {
            createWordOnBoardInRow(0, y, 0, new Word(sb.toString()));
        }

        board.clear();

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                assertNull(board.getLetter(x, y));
            }
        }
    }

    @Test
    public void LetterMovedDownIfEmptyBelow() {
        createWordOnBoardInRow(0, 17, 0, new Word("abc"));
        createWordOnBoardInRow(0, 18, 0, new Word("test"));
        board.setCurrentLetter(null, 0, 0);
        board.moveLetterDownIfEmptyBelow();
        assertEquals('t', board.getLetter(0, 19).getCharacter());
        assertEquals('e', board.getLetter(1, 19).getCharacter());
        assertEquals('a', board.getLetter(0, 18).getCharacter());
        assertEquals('b', board.getLetter(1, 18).getCharacter());
        assertNull(board.getLetter(0, 17));
        assertNull(board.getLetter(1, 17));
    }

    private void createWordOnBoardInRow(int x, int y, int distanceBetweenLetters, Word word) {
        for (Letter letter : word) {
            letter.setColor(LetterColor.RED);
            board.setCurrentLetter(letter, x, y);
            x++;
            x += distanceBetweenLetters;
        }
    }

    private void createWordOnBoardColumn(int x, int y, int distanceBetweenLetters, Word word) {
        for (Letter letter : word) {
            letter.setColor(LetterColor.RED);
            board.setCurrentLetter(letter, x, y);
            y++;
            y += distanceBetweenLetters;
        }
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

        return wordList;
    }
}
