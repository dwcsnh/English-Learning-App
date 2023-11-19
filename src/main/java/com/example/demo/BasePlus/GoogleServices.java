package com.example.demo.BasePlus;

import java.io.*;
import java.net.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class GoogleServices {
    private static final String APP_SCRIPT_DEPLOYMENT_URL = "https://script.google.com/macros/s/AKfycbxhzIFzgP4kqY0gSqtsdx8XaeohE8CZVrH18EylAE-D7aA08dcVF5FoTJ3I6Jilg-Os/exec";
    private static final String GOOGLE_TRANSLATE_PRONUNCIATION_URL = "https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=";
    private String sourceLanguage = "en";
    private String targetLanguage = "vi";

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String sentenceTranslation(String text) {
        String targetURL = generateTranslationURL(text);
        System.out.println(targetURL);
        return readURLContent(targetURL);
    }

    public String generateTranslationURL(String text) {
        text = text.replace(" ", "%20").replace("\n", "").replace("\r", "");
        return APP_SCRIPT_DEPLOYMENT_URL + "?text=" + text + "&source=" + sourceLanguage + "&target=" + targetLanguage;
    }

    public static String generatePronunciationURL(String text, String language) {
        text = text.replace(" ", "%20").replace("\n", "").replace("\r", "");
        return GOOGLE_TRANSLATE_PRONUNCIATION_URL + language + "&client=tw-ob&q=" + text;
    }

    public String readURLContent(String targetURL) {
        URL url;
        String line = "";
        try {
            url = new URL(targetURL);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isReader);
            line = bufferedReader.readLine();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void pronounce(String text, String language) {
        URL url;
        String audioURL = generatePronunciationURL(text, language);
        System.out.println(audioURL);
        try {
            url = new URL(audioURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream pronunciationAudio = connection.getInputStream();
                try {
                    Player player = new Player(new BufferedInputStream(pronunciationAudio));
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
