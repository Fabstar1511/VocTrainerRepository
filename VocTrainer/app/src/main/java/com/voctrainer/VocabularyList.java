package com.voctrainer;

/*
 This class describes a set of vocabularies
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class VocabularyList{

    private ArrayList<Vocabulary> vocList = new ArrayList<Vocabulary>();

    public VocabularyList(){

    }

    public VocabularyList(ArrayList<Vocabulary> list){
        this.vocList = list;
    }

    public ArrayList<Vocabulary> getVocList(){
        return this.vocList;
    }

    public void add(Vocabulary voc){
        this.vocList.add(voc);
    }

    public ArrayList<Vocabulary> getList(){
        return this.vocList;
    }

    public Vocabulary getVocabularyById(int i){
        if(this.vocList.isEmpty()) return null;
        else if(i < this.vocList.size()) return this.vocList.get(i);
        else return null;
    }

    public int getSize(){
        return this.vocList.size();
    }

    /*
     Counts number of correct answers
     */
    public int countCorrectAnswers(){
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
