package com.voctrainer;

/*
 This class describes a set of vocabularies
 */

import java.util.ArrayList;
import java.util.Random;

public class VocabularyList {

    private ArrayList<Vocabulary> vocList = new ArrayList<Vocabulary>();

    public VocabularyList(){

    }

    public void add(Vocabulary voc){
        this.vocList.add(voc);
    }

    /*
     Counts number of correct answers
     */
    private int countCorrectAnswers(){
        int number = 0;
        if(!this.vocList.isEmpty()){
            for(Vocabulary voc : this.vocList){
                if(voc.checkAnswerWasCorrect()){
                    number++;
                }
            }
        }
        return number;
    }

    /*
    Calculates percentage of correct answers from 0% to 100%
    */
    public int getPercentageOfCorrectAnswers(){
        return (countCorrectAnswers() / this.vocList.size()) * 100;
    }

    /*
     Randomized Order of the Vocabulary List
     */
    public void randomizeOrder(){
        if(this.vocList.size() > 1){
            ArrayList<Vocabulary> vocListTemp = new ArrayList<Vocabulary>();
            for(Vocabulary voc : this.vocList){
                vocListTemp.add(voc);
            }
            this.vocList.clear();

            Random rand = new Random();
            int r = 0;

            while(!vocListTemp.isEmpty()){
                r = rand.nextInt(vocListTemp.size());
                this.vocList.add(vocListTemp.get(r));
                vocListTemp.remove(r);
            }
            //String Test = "";
            //for(int i = 0; i < this.vocList.size(); i++){
            //    Test = Test + i + ". " + this.vocList.get(i) + "\n";
            //}
        }
    }
}
