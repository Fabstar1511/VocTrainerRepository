package com.voctrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Result extends AppCompatActivity implements View.OnClickListener{

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";
    private final String CURRENT_QUIZ_RESULT = "currentQuizProgress";

    private int areaID;
    private int level;
    private int progress;
    private int quizResult;

    public Button btn_continue;

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
    }

    // Progress is saved as an integer between 0 and 100 (percent)
    private void saveUserData(String key, int Progress){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, Progress);
    }

       /*
    private void saveAllUserData(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(USER_DATA_FG0_LVL1, this.progress_Lvl_1[0]);
        editor.putInt(USER_DATA_FG0_LVL2, this.progress_Lvl_2[0]);
        editor.putInt(USER_DATA_FG0_LVL3, this.progress_Lvl_3[0]);

        editor.putInt(USER_DATA_FG1_LVL1, this.progress_Lvl_1[1]);
        editor.putInt(USER_DATA_FG1_LVL2, this.progress_Lvl_2[1]);
        editor.putInt(USER_DATA_FG1_LVL3, this.progress_Lvl_3[1]);

        editor.putInt(USER_DATA_FG2_LVL1, this.progress_Lvl_1[2]);
        editor.putInt(USER_DATA_FG2_LVL2, this.progress_Lvl_2[2]);
        editor.putInt(USER_DATA_FG2_LVL3, this.progress_Lvl_3[2]);

        editor.putInt(USER_DATA_FG3_LVL1, this.progress_Lvl_1[3]);
        editor.putInt(USER_DATA_FG3_LVL2, this.progress_Lvl_2[3]);
        editor.putInt(USER_DATA_FG3_LVL3, this.progress_Lvl_3[3]);

        editor.putInt(USER_DATA_FG4_LVL1, this.progress_Lvl_1[4]);
        editor.putInt(USER_DATA_FG4_LVL2, this.progress_Lvl_2[4]);
        editor.putInt(USER_DATA_FG4_LVL3, this.progress_Lvl_3[4]);
        editor.apply();
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_skip) {
            Intent intent = new Intent(Result.this, Congratulation.class);
            startActivity(intent);
            this.finish();
        }
    }
}