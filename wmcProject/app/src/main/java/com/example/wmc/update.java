package com.example.wmc;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update extends AppCompatActivity {
    private EditText et_userName;
    private EditText et_userEmail;
    private EditText et_userPsw;

    private ImageButton imgbtn_userName;
    private ImageButton imgbtn_userEmail;
    private ImageButton imgbtn_userPsw;

    private Button btn_update;

    private extraFunctions extraFunctions;

    private String id;
    private String name;
    private String email;
    private String psw;

    private ApiInterface apiInterface;

    private userData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(update.this, update.this);

        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userEmail = (EditText) findViewById(R.id.et_userEmail);
        et_userPsw = (EditText) findViewById(R.id.et_userPsw);

        btn_update = (Button) findViewById(R.id.btn_update);

        imgbtn_userName = (ImageButton) findViewById(R.id.imgbtn_uName);
        imgbtn_userEmail = (ImageButton) findViewById(R.id.imgbtn_uEmail);
        imgbtn_userPsw = (ImageButton) findViewById(R.id.imgbtn_uPsw);

        id = getIntent().getStringExtra("uId");
        name = getIntent().getStringExtra("uName");
        email = getIntent().getStringExtra("uEmail");
        psw = getIntent().getStringExtra("uPsw");

        et_userName.setText(name);
        et_userEmail.setText(email);
        et_userPsw.setText(psw);

        imgbtn_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(update.this,et_userName,btn_update);
            }
        });

        imgbtn_userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(update.this,et_userEmail,btn_update);
            }
        });

        imgbtn_userPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.openKeyboard(update.this,et_userPsw,btn_update);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(update.this, update.this);
                extraFunctions.showProgressDialog(update.this, "Updating User");

                name = et_userName.getText().toString();
                email = et_userEmail.getText().toString();
                psw = et_userPsw.getText().toString();
                updateUser();
            }
        });
    }

    private void updateUser() {

        userData = new userData(name,email,psw);

        final Call<userData> userDataCall = apiInterface.updateUser(id, userData);
        userDataCall.enqueue(new Callback<userData>() {
            @Override
            public void onResponse(Call<userData> call, Response<userData> response) {
                String updateStatus = response.body().getUpdatestatus();
                if (updateStatus.equals("updated")) {

                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.uupdated));
                    extraFunctions.toast.show();
                    finish();
                }
                else {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.sww));
                    extraFunctions.toast.show();
                }
            }

            @Override
            public void onFailure(Call<userData> call, Throwable t) {
                extraFunctions.hideProgressDialog();
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });

    }

}
