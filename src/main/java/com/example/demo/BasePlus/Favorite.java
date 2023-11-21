package com.example.demo.BasePlus;

public class Favorite extends DictionaryManagement {
    public Favorite(String path) {
        super(path);
    }

    /*
     * add word to favorite list using binary search.
     * write entire favorite list to favorite.txt.
     * when open -> read the data to favorite list.
     * when unfavorite -> remove from favorite list -> rewrite the txt file.
     * */

    public void addWordToFavorite(Word word) {
        int size = this.dictionary.getWordList().size();
        int index = this.dictionary.insertIndex(0, size - 1, word.getSpelling());
        this.dictionary.getWordList().add(index, word);
        this.writeToFile(this.dictionary.getWordList());
    }

    public void removeWordFromFavorite(Word word) {
        this.dictionary.removeWord(word.getSpelling());
        this.writeToFile(this.dictionary.getWordList());
    }
}
