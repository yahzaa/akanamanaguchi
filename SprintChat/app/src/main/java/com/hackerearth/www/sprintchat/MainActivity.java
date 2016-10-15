package com.hackerearth.www.sprintchat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;


public class MainActivity extends AppCompatActivity {

    private FetchHackathonList _FetchHackathonList;
    String hackathon_list_url = "https://devx-staging7-b40sd1.hackerearth.com/sprints/get-registered-events/";
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle basket = getIntent().getExtras();
        username = basket.getString("username");


        _FetchHackathonList = new FetchHackathonList();
        _FetchHackathonList.execute();
    }

    private class FetchHackathonList extends AsyncTask<String, String, String> {

        private Context mContext;

        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try {
                URL url = new URL(hackathon_list_url + username);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }

            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            LinearLayout hackathon_list = (LinearLayout) findViewById(R.id.hackathon_list);
            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            try {
                JSONObject obj = new JSONObject(result);

                String error_msg = obj.getString("error_msg");

                if(error_msg == "null") {
                    JSONArray event_list = new JSONArray(obj.getString("event_list"));
                    final String username = obj.getString("username");
                    int event_list_length = event_list.length();
                    for (int i = 0; i < event_list_length; i++) {
                        int position = event_list_length - i - 1;
                        JSONObject event = new JSONObject(event_list.get(position).toString());
                        Button hackathon_button = new Button(MainActivity.this, null, R.attr.borderlessButtonStyle);
                        final String eventSlug = event.get("event_slug").toString();
                        hackathon_button.setText(event.get("event_name").toString());
                        hackathon_button.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                intent.putExtra("roomName", eventSlug);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            }
                        });
                        hackathon_list.addView(hackathon_button, layout_params);
                    }

                }
                else {
                    EditText error_label = new EditText(MainActivity.this);
                    error_label.setText(error_msg);
                    error_label.setKeyListener(null);
                    hackathon_list.addView(error_label, layout_params);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

