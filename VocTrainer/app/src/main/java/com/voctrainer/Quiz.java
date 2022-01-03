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

public class Quiz extends AppCompatActivity implements View.OnClickListener{

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
    public Button btn_DEBUG_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui10_quiz);
        this.setTitle("Quiz - Level 1");
        radioGroup = findViewById(R.id.radioGroup);

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);

        btn_DEBUG_skip = (Button) findViewById(R.id.button_DEBUG_SkipResult);
        btn_DEBUG_skip.setText("To result");
        btn_DEBUG_skip.setOnClickListener(this);

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
        radioButtonb.setChecked(false);
        RadioButton radioButtonc = findViewById(R.id.radioButton_c);
        radioButtonc.setText("Coil");
        radioButtonc.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_DEBUG_SkipResult) {
            Intent intent = new Intent(Quiz.this, Result.class);
            intent.putExtra(SELECTED_AREA, areaID);
            intent.putExtra(SELECTED_LEVEL, level);
            intent.putExtra(LEVEL_PROGRESS, progress);
            intent.putExtra(CURRENT_QUIZ_RESULT, quizResult);
            startActivity(intent);
            this.finish();
        }
    }
}