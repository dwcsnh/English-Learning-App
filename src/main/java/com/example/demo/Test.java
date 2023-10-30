package com.example.demo;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
//        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
//        dictionaryCommandline.dictionaryBasic();
        Scanner input = new Scanner(System.in);
        GoogleServices services = new GoogleServices();
        String text = "";
        while (true) {
            text = input.nextLine();
            System.out.println(services.sentenceTranslation(text));
            services.pronounce(text);
        }
    }
}
