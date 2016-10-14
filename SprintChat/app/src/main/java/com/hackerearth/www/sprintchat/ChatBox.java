package com.hackerearth.www.sprintchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatBox extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView message_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        message_history.setAdapter(adapter);

        final Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
    }
}
