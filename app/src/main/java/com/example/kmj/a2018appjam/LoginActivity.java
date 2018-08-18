package com.example.kmj.a2018appjam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText mid;
    EditText mpassword;
    Button mlogin;
    ArrayList<User> user_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mid=findViewById(R.id.id);
        mpassword=findViewById(R.id.password);
        mlogin=findViewById(R.id.login);
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetWorkHelper.getInstance().POSTData(mid.getText().toString() , mpassword.getText().toString()).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        String user_token = response.body().getData();
                        Log.d("sibal",user_token);
                        NetWorkHelper.getInstance().USERData(user_token).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Log.d("token",response.body().getToken());
                                Intent intent = new Intent(LoginActivity.this , ConnectingActivity.class);
                                intent.putExtra("couple_room_token",response.body().getToken());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {

                    }
                });
            }
        });
    }
}
