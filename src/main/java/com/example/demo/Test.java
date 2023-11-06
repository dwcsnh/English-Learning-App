package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {
//        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
//        dictionaryCommandline.dictionaryBasic();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        ArrayList<Word> word = dictionaryManagement.getDictionary();
        ArrayList<String> words = new ArrayList<>();
        for(Word x : word) {
            words.add(x.getSpelling());
        }
        System.out.println(words);
    }
}
