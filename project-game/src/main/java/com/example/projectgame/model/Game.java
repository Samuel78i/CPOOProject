package com.example.projectgame.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private String sentence = "";
    private final int numberOfWords;

    private int lives;
    private boolean isTheLastWordAHeal = false;

    private String language;
    private List<String> dico;

    private List<TextColors> colorsList = new ArrayList<>();

    public Game(int n, String l){
        numberOfWords = n;
        language = l;
        loadDictionary();
        makeMeAGoodSentence();
        lives = 30;
    }

    private void loadDictionary() {
        if (language == null) language = "fr";
        dico = new ArrayList<>();

        try {
            URL resource = getClass().getClassLoader().getResource("Dictionaries/" + language + ".txt");
            assert resource != null;
            Stream<String> lines = Files.lines(Path.of(resource.toURI()), StandardCharsets.UTF_8);
            dico.addAll(lines.parallel().map(line -> removeAccent(line).toLowerCase()).collect(Collectors.toList()));

        } catch (IOException | URISyntaxException e) {
            System.out.println("Erreur de lecture");
        }
    }

    // Source : https://www.developpez.net/forums/d460531/java/general-java/langage/supprimer-accents-chaine/
    public static String removeAccent(String s) {
        s = s.replaceAll("\\p{Punct}", ""); // pour enlever les tirets
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public void makeMeAGoodSentence(){
        for(int i = 0; i<numberOfWords - 3; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(0, dico.size() + 1);
            if(i!=0) {
                sentence += "␣";
            }
            sentence += dico.get(randomNum);
        }
    }


    public String getSentence() {
        return sentence;
    }

    public int getLives(){ return lives;}

    public void plusOneLife(){
        lives++;
    }

    public void minusOneLife(){
        lives--;
    }

    public void aWordHasBeenValidate(int badLetterCounter, int currentLetter) {
        lives -= badLetterCounter;
        sentence = sentence.substring(currentLetter + badLetterCounter);
    }

    public TextColors addAWord(boolean online){
        int randomNum = ThreadLocalRandom.current().nextInt(0, dico.size() + 1);
        int x = sentence.length() +1;
        String wordFromDico = dico.get(randomNum);
        int y = x + wordFromDico.length();
        sentence += "␣" + wordFromDico;

        Random r = new Random();
        int i = r.nextInt(5);
//        if (i == 0) {
            TextColors t = new TextColors(x, y, "blue");
            colorsList.add(t);
            return t;
//        }
//        if(online) {
//            if (i == 1) {
//                TextColors t1 = new TextColors(x, y, "pink");
//                colorsList.add(t1);
//                return t1;
//            }
//        }
        //return null;
    }

    public void addAWordWithNoColor(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, dico.size() + 1);
        int x = sentence.length() +1;
        String wordFromDico = dico.get(randomNum);
        int y = x + wordFromDico.length();
        sentence += "␣" + wordFromDico;
    }



    public String getLastWord() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = sentence.length()-1; i>0; i--){
            if(sentence.charAt(i) == '␣'){
                stringBuilder.append(sentence.charAt(i));
                return stringBuilder.reverse().toString();
            }else{
                stringBuilder.append(sentence.charAt(i));
            }
        }
        return stringBuilder.reverse().toString();
    }


    public boolean isTheLastWordAHeal(){return isTheLastWordAHeal;}

    public List<TextColors> getColorsList() {
        return colorsList;
    }

    public void setColorsList(List<TextColors> colorsList) {
        this.colorsList = colorsList;
    }

    public int howManyWords() {
        int result = 0;
        for(int i = 0; i< sentence.length(); i++){
            if(sentence.charAt(i) == '␣'){
                result++;
            }
        }
        return result+1;
    }

    public void addLives(int y) {
        lives+= y;
    }
}
