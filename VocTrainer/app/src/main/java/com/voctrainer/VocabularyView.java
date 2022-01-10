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
import android.widget.TextView;


import java.util.Random;


public class VocabularyView extends Fragment {

    private static final String PARAM1 = "param1";
    private static final String PARAM2 = "param2";

    private String param1;
    private String param2;
    public Button btn_next;
    public Button btn_back;
    int randomInt = 0;
    Random randomGenerator = new Random();
    VocabularyList vocabulary;
    TextView textdeutsch;
    TextView textenglish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        param1 = savedInstanceState.getString(PARAM1);
        param2 = savedInstanceState.getString(PARAM2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_gui6_vocabulary_view, container, false);
        textdeutsch = v.findViewById(R.id.textView);
        textenglish = v.findViewById(R.id.text);
        btn_next = (Button) v.findViewById(R.id.button_nächste);
        btn_back = (Button) v.findViewById(R.id.button_back);
        return v;
    }

    BroadcastReceiver oriUpdateReceiver = new MGattUpdateRecveiver();

    private class MGattUpdateRecveiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(oriUpdateReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(oriUpdateReceiver);
    }

    private VocabularyService vocabularyService = null;

    private VocabularyConnection vocabularyConnection = new VocabularyConnection();

    private class VocabularyConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            vocabularyService = ((VocabularyService.LocalBinder) service).getService();
            vocabularyService.initialize();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            vocabularyService = null;
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


