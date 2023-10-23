package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Collections.binarySearch;

public class DictionaryManagement {
    private static final String DATA_FILE_PATH = "data\\test.txt";
//    private static final String SPLITTING_CHARACTERS = "<html>";

    private Dictionary dictionary;

    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng từ vựng: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.print("Nhập từ tiếng Anh: ");
            String wordTarget = sc.nextLine();
            System.out.print("Nhập giải thích bằng tiếng Việt: ");
            String wordExplain = sc.nextLine();
            Word word = new Word(wordTarget, wordExplain);
            dictionary.addWord(word);
        }
    }
    public void insertFromFile() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(DATA_FILE_PATH), "UTF8");

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            int splitIndex = line.indexOf("\t");
            System.out.println(splitIndex);
            String wordTarget = line.substring(0, splitIndex);
            String wordExplain = line.substring(splitIndex + 1, line.length());
            Word word = new Word(wordTarget, wordExplain);
            dictionary.addWord(word);
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Word> wordList = dictionary.getWordList();
        while (true) {
            System.out.print("Nhập từ tiếng Anh muốn dịch: ");
            String target = sc.nextLine();
            ArrayList<Word>resultList = dictionary.searcher(target);
            if (!resultList.isEmpty()) {
                Word result = dictionary.lookUp(target);
                if (result != null) {
                    System.out.println("Nghĩa tiếng Việt của từ: " + result.getMeaning());
                } else {
                    System.out.println("Cac tu co the ban muon tim:");
                    for(Word word : resultList) {
                        System.out.println(word.getSpelling());
                    }
                }
            }
        }
    }

    public ArrayList<Word> getDictionary() {
        return dictionary.getWordList();
    }
}
