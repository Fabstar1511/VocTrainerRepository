package com.voctrainer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MovingCounter extends AppCompatActivity implements SensorEventListener, View.OnClickListener{

    public Button btn_help;
    public Button btn_DEBUG_Skip_to_100;
    public Button btn_backToStart;

    private final int MAXIMUM_OF_STEPS = 100;
    private TextView tv_steps;
    private SensorManager sensorManager;
    private Sensor mStepCounter, mStepDetector;
    private boolean isCounterSensorPresent, isDetectorSensorPresent;
    public int stepCount = 0, stepDetect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui2_moving_counter);
        this.setTitle("Schritte zählen");

        btn_help = (Button) findViewById(R.id.button_help);
        btn_help.setText("?");
        btn_help.setOnClickListener(this);

        this.stepDetect = getIntent().getIntExtra("countedSteps", 0);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tv_steps = (TextView) findViewById(R.id.textView_steps);
        tv_steps.setText(String.valueOf(stepDetect));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        btn_DEBUG_Skip_to_100 = (Button) findViewById(R.id.button_Debug_100Steps);
        btn_DEBUG_Skip_to_100.setText("Start");
        btn_DEBUG_Skip_to_100.setOnClickListener(this);
        btn_backToStart = (Button) findViewById(R.id.button_backToStart);
        btn_backToStart.setText("zurück");
        btn_backToStart.setOnClickListener(this);

        /*
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }
        else {
            tv_steps.setText("Counter Sensor is not present");
            isCounterSensorPresent = false;
        }
        */
        init();
    }

    public void init(){
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            mStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isDetectorSensorPresent = true;
        }
        else {
            tv_steps.setText("Detector Sensor is not present!");
            isDetectorSensorPresent = false;
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(MovingCounter.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //if(sensorEvent.sensor == mStepCounter){
        //    stepCount = (int) sensorEvent.values[0];
        //    tv_steps.setText(String.valueOf(stepCount));
        //}
        if(this.stepDetect >= MAXIMUM_OF_STEPS) goToGeoMap();
        if(sensorEvent.sensor == mStepDetector){
            stepDetect = (int) (stepDetect + sensorEvent.values[0]);
            tv_steps.setText(String.valueOf(stepDetect));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        //    sensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
            sensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        //    sensorManager.unregisterListener(this, mStepCounter);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
            sensorManager.unregisterListener(this, mStepDetector);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_help) {
            Intent intent = new Intent(MovingCounter.this, Help.class);
            intent.putExtra("activity_id", 1); //MainActivity is ID = 0
            intent.putExtra("countedSteps", this.stepDetect);
            startActivity(intent);
        }
        else if(v.getId() == R.id.button_backToStart) {
            //sensorManager.unregisterListener(this, mStepCounter);
            sensorManager.unregisterListener(this, mStepDetector);
            Intent intent = new Intent(MovingCounter.this, MainActivity.class);
            startActivity(intent);
            this.finish();
            // if you unregister the hardware will stop detecting steps
            //sensorManager.unregisterListener(this);
        }
        else if(v.getId() == R.id.button_Debug_100Steps) {
            Intent intent = new Intent(MovingCounter.this, ViewSteps.class);
            startActivity(intent);
            this.finish();
        }
    }

    public void goToGeoMap(){
        //sensorManager.unregisterListener(this, mStepCounter);
        sensorManager.unregisterListener(this, mStepDetector);
        Intent intent = new Intent(MovingCounter.this, ViewSteps.class);
        startActivity(intent);
        this.finish();
    }
}