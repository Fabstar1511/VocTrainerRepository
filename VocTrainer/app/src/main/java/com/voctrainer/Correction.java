package com.voctrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Correction extends AppCompatActivity implements View.OnClickListener{

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";
    private final String CURRENT_QUIZ_RESULT = "currentQuizProgress";

    private int areaID;
    private int level;
    private int progress;
    private int quizResult;

    public RadioGroup radioGroup;
    public RadioButton radioButton;
    public Button btn_continue;
    public Button btn_back;

    public RadioButton radioButtonA;
    public RadioButton radioButtonB;
    public RadioButton radioButtonC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_15_correction);
        this.setTitle("Quiz - Level 1");
        radioGroup = findViewById(R.id.radioGroup);

        radioButtonA = (RadioButton) findViewById(R.id.radioButton_a);
        radioButtonB = (RadioButton) findViewById(R.id.radioButton_b);
        radioButtonC= (RadioButton) findViewById(R.id.radioButton_c);

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);

        btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setText("zurück");
        btn_back.setOnClickListener(this);

        btn_continue = (Button) findViewById(R.id.button_next);
        btn_continue.setText("weiter");
        btn_continue.setOnClickListener(this);

        radioButtonA.setEnabled(false);
        radioButtonB.setEnabled(false);
        radioButtonC.setEnabled(false);
    }

    public void callNextQuestion(String msg){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMessage(msg);
            }
        }, 1000);
    }

    public void checkRadioButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        callNextQuestion((String) radioButton.getText());
    }

    public void showMessage(String msg){
        //Toast.makeText(this, "Ausgewählt: " + msg, Toast.LENGTH_LONG).show();
        TextView tV_voc = findViewById(R.id.textView_voc);
        tV_voc.setText("Spule");
        TextView tV_cq = findViewById(R.id.textView_counter_questions);
        tV_cq.setText("Frage 3/3");
        RadioButton radioButtona = findViewById(R.id.radioButton_a);
        radioButtona.setText("Spule");
        radioButtona.setChecked(false);
        RadioButton radioButtonb = findViewById(R.id.radioButton_b);
        radioButtonb.setText("Wrappings");
        radioButtonb.setBackgroundColor(Color.RED);
        radioButtonb.setChecked(true);
        RadioButton radioButtonc = findViewById(R.id.radioButton_c);
        radioButtonc.setText("Coil");
        radioButtonc.setChecked(false);
        radioButtonc.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            Intent intent = new Intent(Correction.this, Correction.class);
            startActivity(intent);
            //this.finish();
        } else if (v.getId() == R.id.button_next) {
            goToCongratulation();
        }
    }

    public void goToCongratulation(){
        Intent intent = new Intent(Correction.this, Congratulation.class);
        intent.putExtra(SELECTED_AREA, areaID);
        intent.putExtra(SELECTED_LEVEL, level);
        intent.putExtra(LEVEL_PROGRESS, progress);
        intent.putExtra(CURRENT_QUIZ_RESULT, quizResult);
        startActivity(intent);
        this.finish();
    }
    public void onBackPressed(){
        goToCongratulation();
    }
}