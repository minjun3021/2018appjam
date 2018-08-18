package com.example.kmj.a2018appjam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectingActivity extends AppCompatActivity {
    BluetoothSPP bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);
        bt = new BluetoothSPP(this);
        if (!bt.isBluetoothEnabled()) {
            bt.enable();
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
        final Intent i = new Intent(getIntent());

        final String couple_room_token = i.getStringExtra("couple_room_token");

        NetWorkHelper.getInstance().HandData(couple_room_token).enqueue(new Callback<Hand>() {
            @Override
            public void onResponse(Call<Hand> call, Response<Hand> response) {
                if(response.body().getHand()){
                    Intent intent = new Intent(ConnectingActivity.this , CompleteActivity.class);
                    intent.putExtra("couple_room_token",couple_room_token);
                    startActivity(intent);
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Hand> call, Throwable t) {

            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener()

        {
            public void onDeviceConnected(String name, String address) {
                //연
                //연결됐을때
                Log.e("connect", "g");
            }

            public void onDeviceDisconnected() {    //연결끊김

            }

            public void onDeviceConnectionFailed() {
                //연결 실패했을때
                Log.e("fail", "sad");
            }
        });

        bt.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {
            public void onNewConnection(String name, String address) {
                //새로운 연결일때
                Log.e("new", "succes");
            }


            public void onAutoConnectionStarted() {
                //자동 연결 시작돼씅ㄹ때
                Log.e("auto", "succes");
            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                //ㄷㅔ이터 받아질때 매번 실행
                NetWorkHelper.getInstance().HandUpdate(couple_room_token).enqueue(new Callback<Hand>() {
                    @Override
                    public void onResponse(Call<Hand> call, Response<Hand> response) {
                        Intent intent = new Intent(ConnectingActivity.this , CompleteActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Hand> call, Throwable t) {

                    }
                });
            }
        });
    }
    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            bt.enable();
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            }
        }
    }

    public void setup() {
        bt.autoConnect("HC-06");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bt.stopService();
    }
}
