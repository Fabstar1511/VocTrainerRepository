package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//ProgressBar
public class ProgressView extends AppCompatActivity implements View.OnClickListener {

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";

    private int areaID;
    private int level;
    private int progress;

    public Button btn_startQuiz;
    public TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui9_progress_view);
        this.setTitle("Fortschritt");

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);

        btn_startQuiz = (Button) findViewById(R.id.button_start_quiz);
        btn_startQuiz.setText("Starte Quiz");
        btn_startQuiz.setOnClickListener(this);

        tvProgress = (TextView) findViewById(R.id.textViewProgress);
        tvProgress.setText(String.valueOf(getLastProgress()));
    }

    public int getLastProgress(){
        return this.progress;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_start_quiz) {
            Intent intent = new Intent(ProgressView.this, Quiz.class);
            intent.putExtra(SELECTED_AREA, areaID);
            intent.putExtra(SELECTED_LEVEL, level);
            intent.putExtra(LEVEL_PROGRESS, progress);
            startActivity(intent);
            this.finish();
        }
    }
}