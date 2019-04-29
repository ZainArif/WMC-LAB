package com.example.wmc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createUser extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private Button btn_save;

    private ApiInterface apiInterface;

    private String username;
    private String email;
    private String password;

    private userData user;

    private String emailPattern;
    private boolean valid;
    //private String sl_type;

    private extraFunctions extraFunctions;

    private String userStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        et_username = (EditText) findViewById(R.id.et_username);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_save = (Button) findViewById(R.id.btn_save);

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // sl_type = getIntent().getStringExtra("sl_type");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(createUser.this, createUser.this);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.closeKeyboard(createUser.this, createUser.this);
                username = et_username.getText().toString();
                email = et_email.getText().toString();
                password = et_password.getText().toString();
                performSignUp();
            }
        });

    }

    private boolean validation() {
        valid = true;
        if (TextUtils.isEmpty(username)) {
            et_username.setError("required");
            valid = false;
        }

        if (TextUtils.isEmpty(email)) {
            et_email.setError("required");
            valid = false;
        } else if (!email.matches(emailPattern)) {
            extraFunctions.text.setText(R.string.iemail);
            extraFunctions.toast.show();
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            et_password.setError("required");
            valid = false;
        }

        return valid;
    }

    private void performSignUp() {

        if (!validation()) {
            return;
        }

        extraFunctions.showProgressDialog(createUser.this, "Authenticating...");

        user = new userData(username, email, password);

        Call<userResponse> userResponseCall = apiInterface.CREATE_USER_RESPONSE_CALL(user);

        userResponseCall.enqueue(new Callback<userResponse>() {
            @Override
            public void onResponse(Call<userResponse> call, Response<userResponse> response) {
                userStatus = response.body().getUserStatus();
                if (userStatus.equals("account created")){
                    finish();
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.uc));
                    extraFunctions.toast.show();

                    et_username.getText().clear();
                    et_email.getText().clear();
                    et_password.getText().clear();

                }
                else if (userStatus.equals(" exist")) {
                    extraFunctions.hideProgressDialog();
                    extraFunctions.text.setText(getResources().getString(R.string.uae));
                    extraFunctions.toast.show();
                }
            }

            @Override
            public void onFailure(Call<userResponse> call, Throwable t) {
                extraFunctions.hideProgressDialog();
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });

    }
}
