package com.example.aurosampleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.auro.scholr.util.AuroScholar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuroScholar.openQuizFragment(this, "7503600686", R.id.home_container);
    }
}
