package com.hackerearth.www.sprintchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        username.setSingleLine();

        final Button submit_email = (Button) findViewById(R.id.submit_email);

        submit_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String w = username.getText().toString();

                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
            }
        });
    }
}
