package com.example.demo;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> wordList = new ArrayList<>();
    private ArrayList<String> wordTargetList = new ArrayList<>();
    private ArrayList<String> wordExplainList = new ArrayList<>();

    public void addWord(Word word) {
        wordList.add(word);
        wordTargetList.add(word.getWordTarget());
        wordExplainList.add(word.getWordExplain());
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public ArrayList<String> getWordTargetList() {
        return wordTargetList;
    }

    public ArrayList<String> getWordExplainList() {
        return wordExplainList;
    }
}
