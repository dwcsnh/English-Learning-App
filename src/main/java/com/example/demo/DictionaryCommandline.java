package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;
    public DictionaryCommandline() throws IOException {
        dictionaryManagement = new DictionaryManagement();
    }
    public void showAllWords(){
        ArrayList<Word> wordList = dictionaryManagement.getDictionary().getWordList();
        //sort ?
        System.out.printf("%-3s | %-10s | %s\n", "NO", "English", "Vietnamese");
        for(int i = 0; i < wordList.size(); i++){
            Word word = wordList.get(i);
            System.out.printf("%-3d | %-10s | %s\n", i+1, word.getSpelling(), word.getMeaning());
        }
    }
    public void dictionaryBasic() throws IOException {
//        dictionaryManagement.insertFromCommandline();
        dictionaryManagement.insertFromFile();
        showAllWords();
        dictionaryManagement.dictionaryLookup();
    }
}
