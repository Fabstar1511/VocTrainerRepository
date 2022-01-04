package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class VocabularyView extends AppCompatActivity implements View.OnClickListener {

    private final String SELECTED_AREA = "selectedArea";
    private final String SELECTED_LEVEL = "selectedLevel";
    private final String LEVEL_PROGRESS = "levelProgress";

    private int curVocId = 0;

    private int areaID;
    private int level;
    private int progress;

    public Button btn_back;
    public Button btn_next;

    public TextView tv_ger;
    public TextView tv_eng;
    VocabularyList vl = new VocabularyList();

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
        btn_next.setText("nächste");
        btn_next.setOnClickListener(this);

        tv_ger = (TextView) findViewById(R.id.tv_german_word);
        tv_eng = (TextView) findViewById(R.id.tv_english_word);

        createVocabularySet();
        setVocabulary(curVocId);
    }

    public void createVocabularySet(){
        Vocabulary voc1  = new Vocabulary("Auto", "car", "house", "money");
        Vocabulary voc2  = new Vocabulary("Maus", "mouse", "muose", "moose");
        Vocabulary voc3  = new Vocabulary("Stuhl", "chair", "table", "couch");
        this.vl.add(voc1);
        this.vl.add(voc2);
        this.vl.add(voc3);
        this.vl.randomizeOrder();
    }

    private void showVocabulary(String word_german, String word_english){
        tv_ger.setText(word_german);
        tv_eng.setText(word_english);
    }

    public void setVocabulary(int i){
        Vocabulary voc = this.vl.getVocabularyById(i);
        showVocabulary(voc.getName(), voc.getName_correct());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            Intent intent = new Intent(VocabularyView.this, ProgressView.class);
            startActivity(intent);
            this.finish();
        }
        else if (v.getId() == R.id.button_next) {
            this.curVocId++;
            if(this.curVocId < this.vl.getSize()) {
                setVocabulary(this.curVocId);
                if((this.curVocId + 1) == this.vl.getSize()) btn_next.setText("bereit");
            }
            else{
                Intent intent = new Intent(VocabularyView.this, ProgressView.class);
                intent.putExtra(SELECTED_AREA, areaID);
                intent.putExtra(SELECTED_LEVEL, level);
                intent.putExtra(LEVEL_PROGRESS, progress);
                startActivity(intent);
                this.finish();
            }
            Toast.makeText(getApplicationContext(),"VocID: " + this.curVocId + ", Size: " + this.vl.getSize(), Toast.LENGTH_LONG).show();
        }
    }
}