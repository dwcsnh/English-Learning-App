package com.example.demo;

import java.io.*;

public class History extends DictionaryManagement {

    /*
    * file path of history file
    * arraylist<word> recentWords
    *
    * addWordToHistory(Word word){
    *   recentWords.add(word); //no need to sort
    *   write the most recent word to history file using bufferedWriter
    * }
    *
    * extends Dictionary to use searcher and lookup function
    *
    * add a base variable to sync recent word list because if using another history object in dictionary controller,
    * there will be 2 recent word list, thus causing file conflict.
    *
    * for ex:
    * add history, dictionary variable to container controller, and then add a container controller variable to the
    * controller. Then use a function to sync the container controller with the variable in other controller.
    * */

    public History(String path) {
        super(path);
    }

    public void addWordToHistory(Word word) {
        this.getDictionary().addWord(word);
        this.writeToFile(word);
        System.out.println("history recorded!");
    }
}
