package com.example.demo.BasePlus;

import java.util.ArrayList;

public class History extends DictionaryManagement {
    public History(String path) {
        super(path);
    }

    public void addWordToHistory(Word word) {
        if (!isExist(word)) {
            this.dictionary.addWord(word);
            this.writeToFile(this.dictionary.getWordList());
        } else {
            this.dictionary.removeWord(word.getSpelling());
            this.dictionary.addWord(word);
            this.writeToFile(this.dictionary.getWordList());
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

    @Override
    public ArrayList<String> getSearcher(String spelling) {
        ArrayList<String> result = new ArrayList<>();
        for (Word word : dictionary.getWordList()) {
            if (word.getSpelling().startsWith(spelling)) {
                result.add(word.getSpelling());
            }
        }
        return result;
    }

    public Word findWord(String target) {
        for(Word word : dictionary.getWordList()) {
            if (word.getSpelling().equals(target)) {
                return word;
            }
        }
        return null;
    }
}
