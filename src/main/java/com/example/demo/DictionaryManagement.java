package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Collections.binarySearch;

public class DictionaryManagement {
    private static final String DATA_FILE_PATH = "data\\E_V.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";

    private Dictionary dictionary;
    private Map<String, Word> mapStringWord = new HashMap<>();

    public DictionaryManagement() throws IOException {
        dictionary = new Dictionary();
        insertFromFile();
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
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String wordSpelling = parts[0];
            String definition = SPLITTING_CHARACTERS + parts[1];
            Word word = new Word(wordSpelling, definition);
            dictionary.addWord(word);
            mapStringWord.put(wordSpelling, word);
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

    public ArrayList<String> getSearcher(String spelling) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Word> words = dictionary.searcher(spelling);
        for (Word word : words) {
            result.add(word.getSpelling());
        }
        return result;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Map<String, Word> getMapStringWord() {
        return mapStringWord;
    }
}