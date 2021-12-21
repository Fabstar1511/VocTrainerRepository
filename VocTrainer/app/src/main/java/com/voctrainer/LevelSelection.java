package com.voctrainer;

import static com.voctrainer.R.drawable.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

    /*
    Loads User data and select level
     */
public class LevelSelection extends AppCompatActivity implements View.OnClickListener{
    /*
    User data Keys
    */
    private final String USER_DATA_FG0_LVL1 = "userDataKeyFg0L1";
    private final String USER_DATA_FG0_LVL2 = "userDataKeyFg0L2";
    private final String USER_DATA_FG0_LVL3 = "userDataKeyFg0L3";

    private final String USER_DATA_FG1_LVL1 = "userDataKeyFg1L1";
    private final String USER_DATA_FG1_LVL2 = "userDataKeyFg1L2";
    private final String USER_DATA_FG1_LVL3 = "userDataKeyFg1L3";

    private final String USER_DATA_FG2_LVL1 = "userDataKeyFg2L1";
    private final String USER_DATA_FG2_LVL2 = "userDataKeyFg2L2";
    private final String USER_DATA_FG2_LVL3 = "userDataKeyFg2L3";

    private final String USER_DATA_FG3_LVL1 = "userDataKeyFg3L1";
    private final String USER_DATA_FG3_LVL2 = "userDataKeyFg3L2";
    private final String USER_DATA_FG3_LVL3 = "userDataKeyFg3L3";

    private final String USER_DATA_FG4_LVL1 = "userDataKeyFg4L1";
    private final String USER_DATA_FG4_LVL2 = "userDataKeyFg4L2";
    private final String USER_DATA_FG4_LVL3 = "userDataKeyFg4L3";

    /*
     progress_Lvl_X[0] = FG_PHYSIK
     progress_Lvl_X[1] = FG_WIRTSCHAFT
     progress_Lvl_X[2] = FG_SE
     progress_Lvl_X[3] = FG_ETECHNIK
     progress_Lvl_X[4] = FG_SOZIOLOGIE
     */

    private int[] progress_Lvl_1 = new int[5];
    private int[] progress_Lvl_2 = new int[5];
    private int[] progress_Lvl_3 = new int[5];

    public Button btn_newArea;
    public Button btn_level1;
    public Button btn_level2;
    public Button btn_level3;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui5_level_selection);
        this.setTitle("Levelstufe wählen");

        btn_level1 = (Button) findViewById(R.id.button_level1);
        btn_level1.setText("Level 1");
        btn_level1.setBackgroundResource(button_bg_round);
        btn_level1.setOnClickListener(this);

        btn_level2 = (Button) findViewById(R.id.button_level2);
        btn_level2.setText("Level 2");
        btn_level2.setBackgroundResource(button_bg_round_unclickable);
        btn_level2.setOnClickListener(this);

        btn_level3 = (Button) findViewById(R.id.button_level3);
        btn_level3.setText("Level 3");
        btn_level2.setBackgroundResource(button_bg_round_unclickable);
        btn_level3.setOnClickListener(this);

        btn_newArea = (Button) findViewById(R.id.button_newArea);
        btn_newArea.setText("Anderes Fachgebiet finden");
        btn_newArea.setOnClickListener(this);

        loadAllUserData();
    }

    public void loadAllUserData(){
        try{
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            this.progress_Lvl_1[0] = sharedPref.getInt(USER_DATA_FG0_LVL1, 0);
            this.progress_Lvl_2[0] = sharedPref.getInt(USER_DATA_FG0_LVL2, 0);
            this.progress_Lvl_3[0] = sharedPref.getInt(USER_DATA_FG0_LVL3, 0);

            this.progress_Lvl_1[1] = sharedPref.getInt(USER_DATA_FG1_LVL1, 0);
            this.progress_Lvl_2[1] = sharedPref.getInt(USER_DATA_FG1_LVL2, 0);
            this.progress_Lvl_3[1] = sharedPref.getInt(USER_DATA_FG1_LVL3, 0);

            this.progress_Lvl_1[2] = sharedPref.getInt(USER_DATA_FG2_LVL1, 0);
            this.progress_Lvl_2[2] = sharedPref.getInt(USER_DATA_FG2_LVL2, 0);
            this.progress_Lvl_3[2] = sharedPref.getInt(USER_DATA_FG2_LVL3, 0);

            this.progress_Lvl_1[3] = sharedPref.getInt(USER_DATA_FG3_LVL1, 0);
            this.progress_Lvl_2[3] = sharedPref.getInt(USER_DATA_FG3_LVL2, 0);
            this.progress_Lvl_3[3] = sharedPref.getInt(USER_DATA_FG3_LVL3, 0);

            this.progress_Lvl_1[4] = sharedPref.getInt(USER_DATA_FG4_LVL1, 0);
            this.progress_Lvl_2[4] = sharedPref.getInt(USER_DATA_FG4_LVL2, 0);
            this.progress_Lvl_3[4] = sharedPref.getInt(USER_DATA_FG4_LVL3, 0);
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(),"Upps ein Fehler:(GetUserData) ist aufgetaucht.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_newArea) {
            Intent intent = new Intent(LevelSelection.this, GeoMap.class);
            startActivity(intent);
            this.finish();
        }
        else if (v.getId() == R.id.button_level1) {
            Intent intent = new Intent(LevelSelection.this, VocabularyView.class);
            startActivity(intent);
            this.finish();
        }
        else if (v.getId() == R.id.button_level2) {
            Toast.makeText(getApplicationContext(),
                    "Dieser Level ist noch nicht freigeschaltet!" +
                            "Absolviere das Quiz auf Level 1 erfolgreich um diesen" +
                            "Level freizuschalten.", Toast.LENGTH_LONG).show();
        }
        else if (v.getId() == R.id.button_level3) {
            Toast.makeText(getApplicationContext(),
                    "Dieser Level ist noch nicht freigeschaltet!" +
                            "Absolviere das Quiz auf Level 2 erfolgreich um diesen" +
                            "Level freizuschalten.", Toast.LENGTH_LONG).show();
        }
    }
}