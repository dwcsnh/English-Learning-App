package com.example.demo;

import java.io.*;
import java.util.ArrayList;

public class Recent {
    private static final int MAX_WORDS = 30;
    private static final String DATA_FILE_PATH = "data\\recent.txt";
    private static ArrayList<String> recentWordList = new ArrayList<>();

    public void setRecentWordList(ArrayList<String> recentWordList) {
        Recent.recentWordList = recentWordList;
    }

    public ArrayList<String> getRecentWordList() {
        return recentWordList;
    }

    public void addWordToRecent(String word) {
        recentWordList.removeIf(e -> e.equals(word));
        recentWordList.add(word);
        refactor();
    }

    public void removeWordToRecent(String word) throws IOException {
        loadRecent();
        boolean isRemove = recentWordList.remove(word);
        if (isRemove) {
            try {
                FileOutputStream out = new FileOutputStream(DATA_FILE_PATH, false);
                StringBuilder content = new StringBuilder();
                for (String target : recentWordList) {
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

    public void exportRecent() throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(DATA_FILE_PATH, true);
            StringBuilder content = new StringBuilder();
            for (String target : recentWordList) {
                content.append(target).append("\n");
            }
            out.write(content.toString().getBytes());
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred.");
        }
    }

    public void loadRecent() throws IOException {
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
            String line;
            while ((line = reader.readLine()) != null) {
                recentWordList.add(line.strip());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't create file!");
        }
    }

    public static void refactor() {
        if (recentWordList.size() <= MAX_WORDS) {
            return;
        }
        while (recentWordList.size() > MAX_WORDS) {
            recentWordList.remove(0);
        }
    }

    public void clearRecent() {
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
