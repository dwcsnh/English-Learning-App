package com.example.demo;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> wordList = new ArrayList<>();

    public void addWord(Word word) {
        wordList.add(word);
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }
}
