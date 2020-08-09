package com.example.appreqchangevar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnToLocalDoor, btnToPublicDoor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToLocalDoor = findViewById(R.id.am_btn_lokal_config);
        btnToPublicDoor = findViewById(R.id.am_btn_public_config);

        btnToLocalDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                directToActivity(LocalDoorActivity.class);
            }
        });

        btnToPublicDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                directToActivity(PublicDoorActivity.class);
            }
        });
    }

    private void directToActivity(Class namaClass){
        startActivity(new Intent(getApplicationContext(), namaClass));
    }
}