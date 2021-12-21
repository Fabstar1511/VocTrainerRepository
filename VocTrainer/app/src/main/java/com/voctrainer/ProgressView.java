package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ProgressView extends AppCompatActivity implements View.OnClickListener {

    public Button btn_startQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui9_progress_view);
        this.setTitle("Fortschritt");

        btn_startQuiz = (Button) findViewById(R.id.button_start_quiz);
        btn_startQuiz.setText("Starte Quiz");
        btn_startQuiz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_start_quiz) {
            Intent intent = new Intent(ProgressView.this, Quiz.class);
            startActivity(intent);
            this.finish();
        }
    }
}