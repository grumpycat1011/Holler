package com.example.holler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class Viewholder_Question extends RecyclerView.ViewHolder{

    TextView email_result,time_result,question_result,phone_result;

    public Viewholder_Question(@NonNull @NotNull View itemView) {
        super(itemView);
    }
    public void subitem(FragmentActivity activity,String email, String time, String question,String phone){
        time_result = itemView.findViewById(R.id.time_item);
        email_result = itemView.findViewById(R.id.Email_item);
        question_result = itemView.findViewById(R.id.Question_item);
        phone_result = itemView.findViewById(R.id.phone_item);
        time_result.setText(time);
        email_result.setText(email);
        question_result.setText(question);
        phone_result.setText(phone) ;
    }
}