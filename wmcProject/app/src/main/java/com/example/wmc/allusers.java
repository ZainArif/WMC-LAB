package com.example.wmc;

import android.service.autofill.UserData;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class allusers extends AppCompatActivity implements SearchView.OnQueryTextListener {
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

    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allusers);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.au));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

//        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                return false;
//            }
//        };
//
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newUser) {
        String userInput = newUser.toLowerCase();
        ArrayList<userData> newUserDataArrayList = new ArrayList<>();

        for (userData user : userDataArrayList) {
            if (user.getName().toLowerCase().contains(userInput))
                newUserDataArrayList.add(user);
        }

        myUserRecycleradapter.updateUsers(newUserDataArrayList);
        return true;
    }
}
