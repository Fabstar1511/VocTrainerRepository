package com.voctrainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Help extends AppCompatActivity implements View.OnClickListener{

    public Button btn_back;
    public TextView tV_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui1a_help);
        this.setTitle("Hilfe");

        btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setText("zurück");
        btn_back.setOnClickListener(this);

        tV_help = (TextView) findViewById(R.id.textView2);

        if(getIntent().hasExtra("activity_id") == true) {
            int activity_id = getIntent().getExtras().getInt("activity_id");
            if(activity_id == 1) {
                tV_help.setText("Deine Schritt werden hier gezählt und angezeigt.\n\nSobald du 100 Schritte\n" +
                        "gemacht hast, kannst du deinen Standort einsehen.");
            }
            else {
                tV_help.setText("Deine Schritt werden gezählt und angezeigt.\n\nSobald du 100 Schritte\n" +
                        "gemacht hast, kannst du deinen Standort einsehen.");
            }

        }
        tV_help.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_back) {
            if(getIntent().hasExtra("activity_id") == true) {
                int activity_id = getIntent().getExtras().getInt("activity_id");
                if(activity_id == 1) {
                    Intent intent = new Intent(Help.this, MovingCounter.class);
                    startActivity(intent);
                    this.finish();
                }
                else {
                    Intent intent = new Intent(Help.this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }

            }
        }
    }
}