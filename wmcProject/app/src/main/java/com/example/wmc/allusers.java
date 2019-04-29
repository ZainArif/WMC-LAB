package com.example.wmc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class allusers extends AppCompatActivity {
    private RecyclerView myUserRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private myUserRecycleradapter myUserRecycleradapter;
    private ArrayList<userData> userDataArrayList;

    private ApiInterface apiInterface;
    private extraFunctions extraFunctions;

    private String id;
    private String email;
    private String name;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        extraFunctions = new extraFunctions();
        extraFunctions.customToast(allusers.this,allusers.this);

        userDataArrayList = new ArrayList<>();
        myUserRecyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        layoutManager = new GridLayoutManager(allusers.this,1);
        myUserRecycleradapter = new myUserRecycleradapter(userDataArrayList,allusers.this);
        myUserRecyclerView.setLayoutManager(layoutManager);
        myUserRecyclerView.setAdapter(myUserRecycleradapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyUsers();
    }

    private void getMyUsers(){
        Call<List<userData>> myUsersList = apiInterface.allUsers();
        myUsersList.enqueue(new Callback<List<userData>>() {
            @Override
            public void onResponse(Call<List<userData>> call, Response<List<userData>> response) {
                userDataArrayList.clear();
                for (int index=0;index<response.body().size();index++){
                    id = response.body().get(index).getId();
                    name = response.body().get(index).getName();
                    email = response.body().get(index).getEmail();
                    password = response.body().get(index).getPassword();
                    userDataArrayList.add(new userData(id,name,email,password));
                    myUserRecycleradapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<userData>> call, Throwable t) {
                extraFunctions.text.setText(getResources().getString(R.string.sww));
                extraFunctions.toast.show();
            }
        });
    }
}
