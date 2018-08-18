package com.example.kmj.a2018appjam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class CompleteActivity extends AppCompatActivity {
    BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
    }
}
