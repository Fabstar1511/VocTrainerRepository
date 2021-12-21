package com.voctrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GeoMap extends AppCompatActivity implements View.OnClickListener{

    public Button btn_DEBUG_Inside_Radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui4_geo_map);
        this.setTitle("Fachgebiet finden");
        //ImageView iv_geomap = (ImageView) findViewById(R.id.imageView_geomap);
        //iv_geomap.setImageResource(R.drawable.image_geomap);
        btn_DEBUG_Inside_Radius = (Button) findViewById(R.id.button_DEBUG_in_Radius);
        btn_DEBUG_Inside_Radius.setText("Skip");
        btn_DEBUG_Inside_Radius.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_DEBUG_in_Radius) {
            Intent intent = new Intent(GeoMap.this, LevelSelection.class);
            startActivity(intent);
            this.finish();
        }
    }
}