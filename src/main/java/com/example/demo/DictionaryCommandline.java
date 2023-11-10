package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;
    private Recent recent;
    private Favorite favorite;
    public DictionaryCommandline(){
        dictionaryManagement = new DictionaryManagement();
        recent = new Recent();
        favorite = new Favorite();
    }
    public void showAllWords(){
        ArrayList<Word> wordList = dictionaryManagement.getDictionary();
        //sort ?
        System.out.printf("%-3s | %-10s | %s\n", "NO", "English", "Vietnamese");
        for(int i = 0; i < wordList.size(); i++){
            Word word = wordList.get(i);
            System.out.printf("%-3d | %-10s | %s\n", i+1, word.getSpelling(), word.getMeaning());
        }
    }
    public void dictionaryBasic() throws IOException {
        //dictionaryManagement.insertFromCommandline();
        //dictionaryManagement.insertFromFile();
        //showAllWords();
        //dictionaryManagement.dictionaryLookup();
        ArrayList<String> li = new ArrayList<>();
        favorite.loadFavorite();
        /*favorite.addWordToFavorite("mancy");
        favorite.addWordToFavorite("mer");
        favorite.addWordToFavorite("ment");*/
        //favorite.exportFavorite();
        //favorite.clearFavorite();
        li = favorite.getFavoriteWordList();
        for (int i = 0; i < li.size(); i++) {
            System.out.println(li.get(i));
        }
        //System.out.println(":))");
    }
}
