package com.voctrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity implements View.OnClickListener{

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";
    private final String CURRENT_QUIZ_RESULT = "currentQuizProgress";

    private int areaID;
    private int level;
    private int progress;
    private int quizResult;
    private VocabularyList selVocList = new VocabularyList();
    private TextView tv_german_word;
    private TextView tv_counterVocs;

    public RadioGroup radioGroup;
    public RadioButton radioButtonSelected;
    public RadioButton radioButtonA;
    public RadioButton radioButtonB;
    public RadioButton radioButtonC;
    public Button btn_DEBUG_skip;

    private int curVocId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui10_quiz);
        this.setTitle("Quiz - Level 1");

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);

        tv_german_word = (TextView) findViewById(R.id.textView_voc);
        tv_counterVocs = (TextView) findViewById(R.id.textView_counter_questions);

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonA = (RadioButton) findViewById(R.id.radioButton_a);
        radioButtonB = (RadioButton) findViewById(R.id.radioButton_b);
        radioButtonC= (RadioButton) findViewById(R.id.radioButton_c);

        btn_DEBUG_skip = (Button) findViewById(R.id.button_DEBUG_SkipResult);
        btn_DEBUG_skip.setText("To result");
        btn_DEBUG_skip.setOnClickListener(this);

        createVocabularySet();
        setVocabulary(curVocId);
    }

    public void createVocabularySet(){
        Vocabulary voc0  = new Vocabulary("Auto", "car", "house", "money");
        Vocabulary voc1  = new Vocabulary("Maus", "mouse", "muose", "moose");
        Vocabulary voc2  = new Vocabulary("Stuhl", "chair", "table", "couch");
        Vocabulary voc3  = new Vocabulary("Fahrstuhl", "elevator", "loft", "selector");
        Vocabulary voc4  = new Vocabulary("Feier", "party", "feyer", "part");
        Vocabulary voc5  = new Vocabulary("Mann", "man", "woman", "Trans");

        Vocabulary voc6  = new Vocabulary("Blau", "blue", "blou", "blalba");
        Vocabulary voc7  = new Vocabulary("Rot", "red", "orange", "violette");
        Vocabulary voc8  = new Vocabulary("Gelb", "yellow", "submarine", "supermarine");

        Vocabulary voc9  = new Vocabulary("Tag", "day", "night", "fog");
        Vocabulary voc10  = new Vocabulary("Frühstück", "breakfast", "eating", "earlst");
        Vocabulary voc11  = new Vocabulary("Abendessen", "dinner", "lunch", "midnight snack");

        this.selVocList.add(voc0);
        this.selVocList.add(voc1);
        this.selVocList.add(voc2);
        this.selVocList.add(voc3);
        this.selVocList.add(voc4);
        this.selVocList.add(voc5);
        this.selVocList.add(voc6);
        this.selVocList.add(voc7);
        this.selVocList.add(voc8);
        this.selVocList.add(voc9);
        this.selVocList.add(voc10);
        this.selVocList.add(voc11);
        this.selVocList.randomizeOrder();
    }

    private void showVocabulary(String word_german, String word_correct, String word_wrong1, String word_wrong2){
        radioGroup.clearCheck();
        tv_german_word.setText(word_german);
        tv_counterVocs.setText("Frage " + (this.curVocId + 1) + "/" + this.selVocList.getSize());

        ArrayList<String> answersList = new ArrayList<String>();
        answersList.add(word_correct);
        answersList.add(word_wrong1);
        answersList.add(word_wrong2);
        randomizeOrderOnList(answersList);

        radioButtonA.setText(answersList.get(0));
        radioButtonB.setText(answersList.get(1));
        radioButtonC.setText(answersList.get(2));
    }

    public void setVocabulary(int i){
        Vocabulary voc = this.selVocList.getVocabularyById(i);
        showVocabulary(voc.getName(), voc.getName_correct(), voc.getName_wrong1(), voc.getName_wrong2());
    }

    public void callNextQuestion(String answer){
        // We have to set the given answer by the user in the vocabulary object
        this.selVocList.getVocabularyById(this.curVocId).setGivenAnswer(answer);
        this.curVocId++;

        // Show new Vocabulary if still one exists
        if(this.curVocId < this.selVocList.getSize()){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setVocabulary(curVocId);
                }
            }, 1000);
        }
        // Exit Quiz and go to result if no further vocabulary exists
        else{
            this.quizResult = getPercentageOfCorrectAnswers(); // Sets the result to global variable
            goToActivityResult();
        }
    }

    public void checkRadioButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButtonSelected = findViewById(radioID);
        callNextQuestion((String) radioButtonSelected.getText());
    }

    /*
    Calculates percentage of correct answers from 0% to 100%
    */
    public int getPercentageOfCorrectAnswers(){
        double temp1 = (double) this.selVocList.countCorrectAnswers();
        double temp2 = (double) this.selVocList.getSize();
        return (int) ((temp1 / temp2) * 100);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_DEBUG_SkipResult) {
            goToActivityResult();
        }
    }

    public void randomizeOrderOnList(ArrayList<String> strings) {
        if (strings.size() > 1) {
            ArrayList<String> vocListTemp = new ArrayList<String>();
            for (String str : strings) {
                vocListTemp.add(str);
            }
            strings.clear();

            Random rand = new Random();
            int r = 0;

            while (!vocListTemp.isEmpty()) {
                r = rand.nextInt(vocListTemp.size());
                strings.add(vocListTemp.get(r));
                vocListTemp.remove(r);
            }
        }
    }

    public void goToActivityResult(){
        Intent intent = new Intent(Quiz.this, Result.class);
        intent.putExtra(SELECTED_AREA, areaID);
        intent.putExtra(SELECTED_LEVEL, level);
        intent.putExtra(LEVEL_PROGRESS, progress);
        intent.putExtra(CURRENT_QUIZ_RESULT, quizResult);
        startActivity(intent);
        this.finish();
    }
}