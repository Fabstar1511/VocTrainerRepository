package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class VocabularyView extends AppCompatActivity implements View.OnClickListener {

    public Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui6_vocabulary_view);
        this.setTitle("Voc1-Level1");

        btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setText("zurück");
        btn_back.setOnClickListener(this);//NEW
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            Intent intent = new Intent(VocabularyView.this, ProgressView.class);
            startActivity(intent);
            this.finish();
        }
        //else if (v.getId() == R.id.button_help) {

        //}
    }
}