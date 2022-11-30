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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private String sentence = "";
    private final int difficulty;

    private int lives;

    private String language;
    private List<String> dico;

    public Game(int d){
        difficulty = d;
        loadDictionary();
        //makeMeAGoodSentence();
        sentence = "bonjour␣a␣tous␣les␣amis";
        lives = 10;
    }

    private void loadDictionary() {
        if (language == null) language = "fr";
        dico = new ArrayList<>();

        try {
            URL resource = getClass().getClassLoader().getResource("Dictionaries/" + language + ".txt");
            Stream<String> lines = Files.lines(Path.of(resource.toURI()), StandardCharsets.UTF_8);
            dico.addAll(lines.parallel().map(line -> removeAccent(line).toUpperCase()).collect(Collectors.toList()));

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
        for(int i = 0; i<10; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(0, dico.size() + 1);
            sentence += " ";
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
        sentence = sentence.substring(currentLetter);
    }
}
