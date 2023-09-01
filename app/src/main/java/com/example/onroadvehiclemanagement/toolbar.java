package com.example.onroadvehiclemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class toolbar extends AppCompatActivity {


    ImageView playPause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        playPause = (ImageView) findViewById(R.id.playPause);

        playPause.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent maintainIntent = new Intent(toolbar.this, User.class);
                startActivity(maintainIntent);
            }
        });
    }

}