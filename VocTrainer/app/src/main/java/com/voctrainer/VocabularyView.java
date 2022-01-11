package com.voctrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;


public class VocabularyView extends Fragment {
  
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
    public ImageView ivIconArea;
    VocabularyList selVocList = new VocabularyList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        param1 = savedInstanceState.getString(PARAM1);
        param2 = savedInstanceState.getString(PARAM2);

    }

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
        ivIconArea = (ImageView) findViewById(R.id.ivPictureArea);

        setAreaIcon();
        createVocabularySet();
        setVocabulary(curVocId);
    }

    public void createVocabularySet(){
        Vocabulary voc1  = new Vocabulary("Auto", "car", "house", "money");
        Vocabulary voc2  = new Vocabulary("Maus", "mouse", "muose", "moose");
        Vocabulary voc3  = new Vocabulary("Stuhl", "chair", "table", "couch");
        this.selVocList.add(voc1);
        this.selVocList.add(voc2);
        this.selVocList.add(voc3);
        this.selVocList.randomizeOrder();
    }

    private void showVocabulary(String word_german, String word_english){
        tv_ger.setText(word_german);
        tv_eng.setText(word_english);
    }

    public void setVocabulary(int i){
        Vocabulary voc = this.selVocList.getVocabularyById(i);
        showVocabulary(voc.getName(), voc.getName_correct());
    }

    public void setAreaIcon(){
        // Physik=0, Wirtschaft=1, SE=2, ETechnik=3, Soziologie=4
        if(this.areaID == 0) ivIconArea.setImageResource(R.drawable.image_area_physics);
        else if(this.areaID == 1) ivIconArea.setImageResource(R.drawable.image_area_economic);
        else if(this.areaID == 2) ivIconArea.setImageResource(R.drawable.image_area_se);
        else if(this.areaID == 3) ivIconArea.setImageResource(R.drawable.image_area_electrical);
        else if(this.areaID == 4) ivIconArea.setImageResource(R.drawable.image_area_sociology);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            btn_next.setText("nächste");
            this.curVocId--;
            if(this.curVocId < 0){
                Intent intent = new Intent(VocabularyView.this, LevelSelection.class);
                intent.putExtra(SELECTED_AREA, areaID);
                intent.putExtra(SELECTED_LEVEL, level);
                intent.putExtra(LEVEL_PROGRESS, progress);
                startActivity(intent);
                this.finish();
            } else if(this.curVocId >= 0) {
                setVocabulary(this.curVocId);
            }
        }
        else if (v.getId() == R.id.button_next) {
            this.curVocId++;
            if(this.curVocId < this.selVocList.getSize()) {
                setVocabulary(this.curVocId);
                if((this.curVocId + 1) == this.selVocList.getSize()) btn_next.setText("bereit");
            }
            else{
                Intent intent = new Intent(VocabularyView.this, ProgressView.class);
                intent.putExtra(SELECTED_AREA, areaID);
                intent.putExtra(SELECTED_LEVEL, level);
                intent.putExtra(LEVEL_PROGRESS, progress);
                startActivity(intent);
                this.finish();
            }
        }
    }

    private void establishVocabulary(){
        Intent oriServiceIntent = new Intent(requireContext(), VocabularyService.class);
        requireActivity().bindService(oriServiceIntent, vocabularyConnection, AppCompatActivity.BIND_AUTO_CREATE);

    }

    private IntentFilter makeGattUpdateIntentFilter(){
        IntentFilter filter = new IntentFilter();
        return filter;
    }

}


