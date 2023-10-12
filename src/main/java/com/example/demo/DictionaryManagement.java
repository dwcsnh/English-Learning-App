package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Collections.binarySearch;

public class DictionaryManagement {
    private static final String DATA_FILE_PATH = "D:\\.Current_Study\\OOP\\demo\\data\\E_V_Selfmade.txt";
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
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            String wordTarget = parts[0];
            String wordExplain = parts[1];
            Word word = new Word(wordTarget, wordExplain);
            dictionary.addWord(word);
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Word> wordList = dictionary.getWordList();
        while (true) {
            System.out.print("Nhập từ tiếng Anh muốn dịch: ");
            String wordTarget = sc.nextLine();
            System.out.print("Nghĩa tiếng Việt của từ: ");
            String wordExplain = "";
            for(Word word : wordList) {
                wordExplain = "";
                if(word.getSpelling().equalsIgnoreCase(wordTarget)) {
                    wordExplain = word.getMeaning();
                    break;
                }
            }
            if (wordExplain != "") {
                System.out.println(wordExplain);
            }
            else {
                System.out.println("targetWord not found!!");
            }
        }
    }

    public ArrayList<Word> getDictionary() {
        return dictionary.getWordList();
    }
}
