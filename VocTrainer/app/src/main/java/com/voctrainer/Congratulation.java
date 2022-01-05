package com.voctrainer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Congratulation extends AppCompatActivity implements View.OnClickListener{

    public Button btn_continue;
    public Button btn_correction;
    public TextView tvRes;

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";
    private final String CURRENT_QUIZ_RESULT = "currentQuizProgress";

    private final int LEVEL_UP = 70; // Level is reached of a progress quote of 70%

    private int areaID;
    private int level;
    private int progress;
    private int quizResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui14_congratulation);
        this.setTitle("Quiz beenden");

        btn_continue = (Button) findViewById(R.id.button_exit);
        btn_continue.setText("weiter");
        btn_continue.setOnClickListener(this);

        btn_correction = (Button) findViewById(R.id.button_correction);
        btn_correction.setText("Korrektur");
        btn_correction.setOnClickListener(this);

        tvRes = (TextView) findViewById(R.id.tvResult);

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);
        this.quizResult = getIntent().getIntExtra(CURRENT_QUIZ_RESULT, 0);
        showMessage();
    }

    public void showMessage(){
        // :) Last Progress >= 70% && Current Progress >= 70%
        if((this.progress >= LEVEL_UP) && (this.quizResult >= LEVEL_UP)){
            // Current Progress > Last Progress
            if(this.quizResult > this.progress) tvRes.setText("Gratulation!\n" + "Du hast hast dich verbessert!\nWeiter so! :)");
            // Current Progress <= Last Progress
            else tvRes.setText("Quiz erfolgreich absolviert.\n" + "Du hast dich aber nicht verbessert. :]");
        }
        // :) Last Progress < 70% && Current Progress >= 70%
        else if((this.progress < LEVEL_UP) && (this.quizResult >= LEVEL_UP)){
            int newLevel = this.level + 1;
            tvRes.setText("Gratulation!\n" + "Du hast Level " + newLevel + " erreicht!");
        }
        // Last Progress >= 70% && Current Progress < 70% :(
        else if((this.progress >= LEVEL_UP) && (this.quizResult < LEVEL_UP)){
            tvRes.setText("Du hast wohl einige Vokabeln vergessen." + "\n\nDu bleibst auf Level " + this.level
                    + "\n\n Versuche das Quiz später erneut!");
        }
        // Last Progress < 70% && Current Progress < 70% :(
        else {
            tvRes.setText("Du bleibst auf Level " + this.level
                    + "\n\n Versuche das Quiz später erneut!");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_exit) {
            Intent intent = new Intent(Congratulation.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else if (v.getId() == R.id.button_correction) {
            Intent intent = new Intent(Congratulation.this, Correction.class);
            startActivity(intent);
            this.finish();
        }
    }
}