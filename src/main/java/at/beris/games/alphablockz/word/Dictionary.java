/*
 * This file is part of Alphablockz.
 *
 * Copyright 2015-2017 by Bernd Riedl <bernd.riedl@gmail.com>
 *
 * Licensed under GNU General Public License 3.0 or later.
 * Some rights reserved. See COPYING, AUTHORS.
 */

package at.beris.games.alphablockz.word;

import at.beris.games.alphablockz.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dictionary {

    public static final int WORD_MIN_LENGTH = 3;
    public static final int WORD_MAX_LENGTH = 10;

    private static Dictionary instance;

    private Map<Integer, List<String>> wordListByWordLength;

    private Dictionary() {
        wordListByWordLength = new HashMap<Integer, List<String>>();
    }

    public static Dictionary getInstance() {
        if (instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

    public void clear() {
        for (Map.Entry<Integer, List<String>> entry : wordListByWordLength.entrySet()) {
            entry.getValue().clear();
        }

        wordListByWordLength.clear();
    }

    public boolean contains(String word) {
        boolean contains = false;
        List<String> wordList = wordListByWordLength.get(word.length());

        if (wordList != null)
            contains = wordListByWordLength.get(word.length()).contains(word);

        return contains;
    }

    public void add(String word) {
        List<String> wordList = wordListByWordLength.get(word.length());

        if (wordList == null) {
            wordList = new ArrayList<String>();
            wordListByWordLength.put(word.length(), wordList);
        }

        wordList.add(word);
    }

    public String get(int wordLength, int index) {
        return wordListByWordLength.get(wordLength).get(index);
    }

    public int size(int wordLength) {
        return wordListByWordLength.get(wordLength).size();
    }

    public int size() {
        int size = 0;

        for (List<String> wordList : wordListByWordLength.values()) {
            size += wordList.size();
        }

        return size;
    }

    public void load() {
        BufferedReader inputStream = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("words_en.txt")));
        String word;
        try {
            while ((word = inputStream.readLine()) != null) {
                add(word);
            }
        } catch (IOException e) {
            Application.logException(e);
        }
    }

    public String getRandomWord() {
        Random rand = new Random();

        int randomWordLength = 0;
        int randomWordListIndex = rand.nextInt(wordListByWordLength.size());
        int index = 0;
        for (Integer wordLength : wordListByWordLength.keySet()) {
            if (index == randomWordListIndex) {
                randomWordLength = wordLength;
                break;
            }
            index++;
        }

        return get(randomWordLength, rand.nextInt(this.wordListByWordLength.get(randomWordLength).size()));
    }

    public String getRandomWord(int wordLength) {
        Random rand = new Random();
        return get(wordLength, rand.nextInt(wordListByWordLength.get(wordLength).size()));
    }
}
