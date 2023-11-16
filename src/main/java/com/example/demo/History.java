package com.example.demo;

import java.io.*;

public class History extends DictionaryManagement {
    public History(String path) {
        super(path);
    }

    public void addWordToHistory(Word word) {
        if (!isExist(word)) {
            this.dictionary.addWord(word);
            this.writeToFile(this.dictionary.getWordList());
            System.out.println("history recorded!");
        } else {
            this.dictionary.getWordList().remove(word);
            this.dictionary.addWord(word);
            this.writeToFile(this.getDictionary().getWordList());
        }
    }

    @Override
    public boolean isExist(Word target) {
        for (Word word : dictionary.getWordList()) {
            if (word.getSpelling().equals(target.getSpelling())) {
                return true;
            }
        }
        return false;
    }
}
