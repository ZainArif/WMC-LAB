package com.example.wmc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myUserRecycleradapter extends RecyclerView.Adapter<myUserViewHolder> {
    private ArrayList<userData> userDataArrayList;
    private Context context;
    private extraFunctions extraFunctions;
    private ApiInterface apiInterface;
    private Activity activity;

    public myUserRecycleradapter(ArrayList<userData> userDataArrayList, Context context) {
        this.userDataArrayList = userDataArrayList;
        this.context = context;
        activity = (Activity)context;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity) context, context);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public myUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View myUserView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_all_users, parent, false);
        myUserViewHolder myUserViewHolder = new myUserViewHolder(myUserView);
        return myUserViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myUserViewHolder myUserViewHolder, final int position) {
        final userData userDataDetails = userDataArrayList.get(position);
        myUserViewHolder.tv_userName.setText(userDataDetails.getName());

        myUserViewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraFunctions.showProgressDialog(context,"Deleting User");
                Call<userData> delUserCall = apiInterface.delUser(userDataDetails.getId());
                delUserCall.enqueue(new Callback<userData>() {
                    @Override
                    public void onResponse(Call<userData> call, Response<userData> response) {
                        String delStatus = response.body().getDelstatus();
                        if (delStatus.equals("deleted")) {
                            userDataArrayList.remove(position);
                            notifyItemRemoved(position);
                            notifyDataSetChanged();
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(context.getResources().getString(R.string.udeleted));
                            extraFunctions.toast.show();
                        } else {
                            extraFunctions.hideProgressDialog();
                            extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                            extraFunctions.toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<userData> call, Throwable t) {
                        extraFunctions.hideProgressDialog();
                        extraFunctions.text.setText(context.getResources().getString(R.string.sww));
                        extraFunctions.toast.show();
                    }
                });
            }
        });

        myUserViewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update_Activity = new Intent(activity, update.class);
                update_Activity.putExtra("uId",userDataDetails.getId());
                update_Activity.putExtra("uName",userDataDetails.getName());
                update_Activity.putExtra("uEmail",userDataDetails.getEmail());
                update_Activity.putExtra("uPsw",userDataDetails.getPassword());
                activity.startActivity(update_Activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDataArrayList.size();
    }

    public void updateUsers(ArrayList<userData> userDataArrayList){
        this.userDataArrayList = new ArrayList<>();
        this.userDataArrayList.addAll(userDataArrayList);
        notifyDataSetChanged();
    }
}
