package com.example.wmc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createUser;
    private Button allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createUser = (Button)findViewById(R.id.btn_createuser);
        allUsers = (Button)findViewById(R.id.btn_allusers);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser_Activity = new Intent(MainActivity.this, createUser.class);
                startActivity(createUser_Activity);
            }
        });

        allUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allUsers_Activity = new Intent(MainActivity.this, allusers.class);
                startActivity(allUsers_Activity);
            }
        });
    }
}
