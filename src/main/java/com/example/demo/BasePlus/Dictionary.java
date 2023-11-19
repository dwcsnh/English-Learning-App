package com.example.demo.BasePlus;

import java.util.ArrayList;

public class Dictionary {
    protected ArrayList<Word> wordList = new ArrayList<>();

    public void addWord(Word word) {
        wordList.add(word);
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }
    public void setWordList(ArrayList<Word> words) {
        wordList = words;
    }

    public void addWordByBinary(Word word){
        int i = insertIndex(0, wordList.size() - 1, word.getSpelling());
        wordList.add(i, word);
    }

    /**
     * Find the index of the given word in the word list.
     * @param start
     * @param end
     * @param spelling
     * @return the word's index if the word is in the word list, otherwise return -1
     */
    public int findWordIndex(int start, int end, String spelling) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            Word word = wordList.get(mid);
            int result = word.getSpelling().compareTo(spelling);
            if (result == 0) {
                return mid;
            } else if (result < 0) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Find the proper position for a word that not in the list using binary search.
     * @param start
     * @param end
     * @param spelling
     * @return
     */
    public int insertIndex(int start, int end, String spelling) {
        if (start > end) {
            return start;
        } else {
            int mid = start + (end - start) / 2;
            Word word = wordList.get(mid);
            int result = word.getSpelling().compareTo(spelling);
            if (result == 0) {
                return mid;
            } else if (result < 0) {
                return insertIndex(mid + 1, end, spelling);
            } else {
                return insertIndex(start, mid - 1, spelling);
            }
        }
    }
    /**
     * Find a specific word.
     * @param spelling
     * @return
     */
    public Word lookUp(String spelling) {
        //find a specific word
        int size = wordList.size();
        int index = findWordIndex(0, size - 1, spelling);
        if (index >= 0) {
            return wordList.get(index);
        } else {
            return null;
        }
    }
    /**
     * Find a list of words starting with the given input.
     * @param input
     * @return
     */
    public ArrayList<Word> searcher(String input) {
        ArrayList<Word> result = new ArrayList<>();
        int size = wordList.size();
        int start = insertIndex(0, size - 1, input);
        Word word = wordList.get(start);
        while (word.getSpelling().startsWith(input)) {
            result.add(word);
            word = wordList.get(++start);
        }
        return result;
    }

    public void removeWord(String spelling) {
        for (Word word : wordList) {
            if (word.getSpelling().equals(spelling)) {
                wordList.remove(word);
                break;
            }
        }
    }
}