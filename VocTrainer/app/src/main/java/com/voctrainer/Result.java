package com.voctrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Result extends AppCompatActivity implements View.OnClickListener{

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";
    private final String CURRENT_QUIZ_RESULT = "currentQuizProgress";

    private final int LEVEL_UP = 70; // Level is reached of a progress quote of 70%

    private int areaID;
    private int level;
    private int progress;
    private int quizResult;

    public Button btn_continue;
    TextView tvRes;
    TextView tvDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui13_result);
        this.setTitle("Dein Ergebnis");

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);
        this.quizResult = getIntent().getIntExtra(CURRENT_QUIZ_RESULT, 0);

        btn_continue = (Button) findViewById(R.id.button_skip);
        btn_continue.setText("weiter");
        btn_continue.setOnClickListener(this);
        tvRes = findViewById(R.id.tvQuizResult);
        tvDis = findViewById(R.id.tvResult);
        saveUserData(buildKey(this.areaID, this.level), this.quizResult);
        showResult();
    }

    // Progress is saved as an integer between 0 and 100 (percent)
    private void saveUserData(String key, int Progress){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, Progress);
        editor.apply();
    }

    public String buildKey(int area, int level){
        //FORMAT: "userDataKeyFg0L1"
        String result = "userDataKeyFg";
        return result + area + "L" + level;
    }

    public void showResult(){
        if(this.quizResult >= LEVEL_UP){
            tvDis.setText("Du hast das Quiz bestanden!");
            tvRes.setText(this.quizResult + "%");
        }
        else {
            tvRes.setTextColor((Color.argb(255, 255, 50, 0)));
            tvDis.setText("Du hast das Quiz nicht bestanden!");
            tvRes.setText(this.quizResult + "%");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_skip) {
            Intent intent = new Intent(Result.this, Congratulation.class);
            intent.putExtra(SELECTED_AREA, areaID);
            intent.putExtra(SELECTED_LEVEL, level);
            intent.putExtra(LEVEL_PROGRESS, progress);
            intent.putExtra(CURRENT_QUIZ_RESULT, quizResult);
            startActivity(intent);
            this.finish();
        }
    }
}