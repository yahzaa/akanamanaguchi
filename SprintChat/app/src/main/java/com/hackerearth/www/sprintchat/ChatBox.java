package com.hackerearth.www.sprintchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatBox extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private EditText message;
    private ListView message_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        message_history = (ListView) findViewById(R.id.message_history);
        message_history.setAdapter(adapter);

        message = (EditText) findViewById(R.id.message_text);

        final Button send_button = (Button) findViewById(R.id.send_button);
        send_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String msg_text = message.getText().toString();
                if(msg_text.length() > 0){
                    adapter.add(msg_text);
                    message.setText("");
                }
            }
        });
    }
}
