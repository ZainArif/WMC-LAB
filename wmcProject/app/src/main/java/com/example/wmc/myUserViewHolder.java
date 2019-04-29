package com.example.wmc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class myUserViewHolder extends RecyclerView.ViewHolder{
    TextView tv_userName;
    Button btn_edit;
    Button btn_delete;

    public myUserViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_userName = (TextView)itemView.findViewById(R.id.userName);
        btn_edit = (Button)itemView.findViewById(R.id.btn_edit);
        btn_delete = (Button)itemView.findViewById(R.id.btn_delete);
    }
}