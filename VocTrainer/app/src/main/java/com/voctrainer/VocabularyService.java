package com.voctrainer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import  java.io.File;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;

public class VocabularyService extends Service {
    private IBinder binder = new LocalBinder();
    private String path;

    public class LocalBinder extends Binder {
        public VocabularyService getService(){
            return VocabularyService.this;
        }
    }
    public void initialize(){
        setPath(new File(this.getApplicationContext() + "res/raw/Voc_list.xlsx").getAbsolutePath());
        registerReceiver(new MGattUpdateRecveiver(),makeGattUpdateFilter());
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public  void  setPath(String path){
        this.path = path;
        try {
            FileInputStream file = new FileInputStream(new File(path));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            file.close();
        }catch (Exception e){

        }
    }

    private class MGattUpdateRecveiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action =="GET_VOCABULARY"){
                int level = intent.getIntExtra("LEVEL",0);
                String topic = intent.getStringExtra("TOPIC");
                Intent inte = new Intent("SEND_VOCABULARY");
               // inte.putExtra("VOCABULARY", vocal);
                sendBroadcast(inte);
            }
            if(action =="GET_TOPICS"){

            }
        }
    }


    private  IntentFilter makeGattUpdateFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("GET_VOCABULARY");
        filter.addAction("GET_TOPICS");
        return filter;
    }



}