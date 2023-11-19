package com.example.demo.BasePlus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {
    protected String DATA_FILE_PATH;
    protected static final String SPLITTING_CHARACTERS = "<html>";

    protected Dictionary dictionary;
    private Map<String, Word> mapStringWord = new HashMap<>();

    public DictionaryManagement(String path) {
        dictionary = new Dictionary();
        setDataFilePath(path);
        insertFromFile();
    }

    public void setDataFilePath(String path) {
        DATA_FILE_PATH = path;
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

    public void insertFromFile() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(DATA_FILE_PATH), StandardCharsets.UTF_8);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int splitPosition = line.indexOf(SPLITTING_CHARACTERS);
                if (splitPosition > 0 && splitPosition < line.length()) {
                    String spelling = line.substring(0, splitPosition);
                    String definition = line.substring(splitPosition);
                    Word word = new Word(spelling, definition);
                    dictionary.addWord(word);
                    mapStringWord.put(spelling, word);
                }
            }
            System.out.println("inserting...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile(ArrayList<Word> words) {
        try {
            FileWriter fileWriter = new FileWriter(DATA_FILE_PATH);
            for (Word word : words) {
                fileWriter.write(word.getSpelling() + word.getMeaning() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    public boolean isExist(Word target) {
        if(dictionary.lookUp(target.getSpelling()) != null) {
            return true;
        }
        return false;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Map<String, Word> getMapStringWord() {
        return mapStringWord;
    }

    public void print() {
        for (Word word : dictionary.getWordList()) {
            System.out.println(word.getSpelling());
        }
    }

    public void removeWordFromWordList(String spelling) {
        dictionary.removeWord(spelling);
    }
}