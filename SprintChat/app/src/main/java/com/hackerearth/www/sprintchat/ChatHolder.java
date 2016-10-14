package com.hackerearth.www.sprintchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ck on 15/10/16.
 */

public class ChatHolder extends RecyclerView.ViewHolder {
    View mView;

    public ChatHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setUsername(String username) {
        TextView field = (TextView) mView.findViewById(android.R.id.text1);
        field.setText(username);
    }

    public void setText(String text) {
        TextView field = (TextView) mView.findViewById(android.R.id.text2);
        field.setText(text);
    }
}
