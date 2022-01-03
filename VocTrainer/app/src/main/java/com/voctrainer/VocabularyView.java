package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class VocabularyView extends AppCompatActivity implements View.OnClickListener {

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";

    private int areaID;
    private int level;
    private int progress;

    public Button btn_back;
    public Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui6_vocabulary_view);
        this.setTitle("Voc1-Level1");

        this.areaID = getIntent().getIntExtra(SELECTED_AREA, 0);
        this.level = getIntent().getIntExtra(SELECTED_LEVEL, 0);
        this.progress = getIntent().getIntExtra(LEVEL_PROGRESS, 0);

        btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setText("zurück");
        btn_back.setOnClickListener(this);

        btn_next = (Button) findViewById(R.id.button_next);
        btn_next.setText("Nächste");
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            Intent intent = new Intent(VocabularyView.this, VocabularyView.class);
            startActivity(intent);
            this.finish();
        }
        else if (v.getId() == R.id.button_next) {
            Intent intent = new Intent(VocabularyView.this, ProgressView.class);
            intent.putExtra(SELECTED_AREA, areaID);
            intent.putExtra(SELECTED_LEVEL, level);
            intent.putExtra(LEVEL_PROGRESS, progress);
            startActivity(intent);
            this.finish();
        }
    }
}