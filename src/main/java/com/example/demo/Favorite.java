package com.example.demo;

import java.io.*;
import java.util.ArrayList;

public class Favorite {
    private static final int MAX_WORDS = 30;
    private static final String DATA_FILE_PATH = "data\\favorite.txt";
    private static ArrayList<String> favoriteWordList = new ArrayList<>();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();


    public Favorite() {

    }

    public ArrayList<String> getFavoriteWordList() {
        return favoriteWordList;
    }

    public void setFavoriteWordList(ArrayList<String> favoriteWordList) {
        Favorite.favoriteWordList = favoriteWordList;
    }

    public void addWordToFavorite(String word) {
        favoriteWordList.removeIf(e -> e.equals(word));
        favoriteWordList.add(word);
        refactor();
    }

    public void removeWordToFavorite(String word) throws IOException {
        loadFavorite();
        /*System.out.println(word);
        System.out.println(favoriteWordList.indexOf(word));
        System.out.println();
        for (int i = 0; i < favoriteWordList.size(); i++) {
            System.out.println(favoriteWordList.get(i));
        }*/
        boolean isRemove = favoriteWordList.remove(word);
        if (isRemove) {
            try {
                FileOutputStream out = new FileOutputStream(DATA_FILE_PATH, false);
                StringBuilder content = new StringBuilder();
                for (String target : favoriteWordList) {
                    content.append(target).append("\n");
                }
                out.write(content.toString().getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred.");
            }
        }
        else System.out.println("deo");
    }

    public void exportFavorite() throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(DATA_FILE_PATH);
            StringBuilder content = new StringBuilder();
            for (String target : favoriteWordList) {
                dictionaryManagement.insertFromFile();
                Word word_ = dictionaryManagement.getDictionary_().lookUp(target);
                if (word_ != null) {
                    content.append(word_.toString()).append("\n");
                }
            }
            out.write(content.toString().getBytes());
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred.");
        }
    }

    public void loadFavorite() throws IOException {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.out.println("Couldn't create file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't create file!");
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                                    (new FileInputStream(DATA_FILE_PATH), "UTF-8"))) {
            favoriteWordList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) == '-') line = line.substring(1);
                favoriteWordList.add(line.strip());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create file!");
        }
    }

    public static void refactor() {
        if (favoriteWordList.size() <= MAX_WORDS) {
            return;
        }
        while (favoriteWordList.size() > MAX_WORDS) {
            favoriteWordList.remove(0);
        }
    }

    public void clearFavorite() {
        try {
            FileOutputStream out = new FileOutputStream(DATA_FILE_PATH);
            String clear = "";
            out.write(clear.getBytes());
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred.");
        }
    }
}
