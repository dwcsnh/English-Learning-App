package com.example.demo.BasePlus;

public class Word {
    private String spelling;
    private String meaning;
    public Word(String wordTarget, String wordExplain){
        this.spelling = wordTarget;
        this.meaning = wordExplain;
    }
    public String getSpelling(){
        return spelling;
    }
    public void setSpelling(String spelling){
        this.spelling = spelling;
    }
    public String getMeaning(){
        return meaning;
    }
    public void setMeaning(String meaning){
        this.meaning = meaning;
    }
}
