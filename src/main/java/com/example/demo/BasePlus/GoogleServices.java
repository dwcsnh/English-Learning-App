package com.example.demo.BasePlus;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class GoogleServices {
    private static final String APP_SCRIPT_DEPLOYMENT_URL = "https://script.google.com/macros/s/AKfycbxhzIFzgP4kqY0gSqtsdx8XaeohE8CZVrH18EylAE-D7aA08dcVF5FoTJ3I6Jilg-Os/exec";
    private static final String GOOGLE_TRANSLATE_PRONUNCIATION_URL = "https://translate.google.com.vn/translate_tts?ie=UTF-8&tl=en&client=tw-ob&q=";
    private boolean engToVie = true;

    public String sentenceTranslation(String text) {
        if (engToVie) {
            return sentenceTranslation(text, "en", "vi");
        }
        return sentenceTranslation(text, "vi", "en");
    }
    public String sentenceTranslation(String text, String sourceLang, String targetLang) {
        String targetURL = generateTranslationURL(text, sourceLang, targetLang);
        return readURLContent(targetURL);
    }

    public String generateTranslationURL(String text, String sourceLang, String targetLang) {
        if (text.contains(" ")) {
            text = text.replace(" ", "%20");
        }
        return APP_SCRIPT_DEPLOYMENT_URL + "?text=" + text + "&source=" + sourceLang + "&target=" + targetLang;
    }

    public static String generatePronunciationURL(String text) {
        if (text.contains(" ")) {
            text = text.replace(" ", "%20");
        }
        return GOOGLE_TRANSLATE_PRONUNCIATION_URL + text;
    }

    public void setEngToVie(boolean engToVie) {
        this.engToVie = engToVie;
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

    public static void pronounce(String text) {
        URL url;
        String audioURL = generatePronunciationURL(text);
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
