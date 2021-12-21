package com.voctrainer;

/*
 This class describes a single Vocabulary with name, correct answer and two wrong answers
 */

public class Vocabulary {

    private String name = "";
    private String name_correct = "";
    private String name_wrong1 = "";
    private String name_wrong2 = "";
    private String givenAnswer = "";

    public Vocabulary(String name, String correct, String wrong1, String wrong2){
        this.name = name;
        this.name_correct = correct;
        this.name_wrong1 = wrong1;
        this.name_wrong2 = wrong2;
    }

    // Getter
    public String getName(){
        return this.name;
    }
    public String getName_correct(){
        return this.name_correct;
    }
    public String getName_wrong1(){
        return this.name_wrong1;
    }
    public String getName_wrong2(){
        return this.name_wrong2;
    }
    public String getGivenAnswer(){
        return this.givenAnswer;
    }
    // Setter
    public void setGivenAnswer(String answer){
        this.givenAnswer = answer;
    }
    public boolean checkAnswerWasCorrect(){
        return this.name_correct.equals(this.givenAnswer);
    }
}
