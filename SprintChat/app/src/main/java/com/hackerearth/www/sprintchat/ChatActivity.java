package com.hackerearth.www.sprintchat;

import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference ref;
    private List<Message> mMessages = new ArrayList<>();
    private String username = "";
    private String roomName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        Bundle bucket = getIntent().getExtras();
        roomName = bucket.getString("roomName");
        username = bucket.getString("username");

        ref = FirebaseDatabase.getInstance().getReference();

        final RecyclerView recycler = (RecyclerView) findViewById(R.id.messages_recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Message, ChatHolder>(
                Message.class, android.R.layout.two_line_list_item, ChatHolder.class,
                ref.child("chats").child(roomName)) {

            @Override
            public void populateViewHolder(ChatHolder chatViewHolder, Message message, int position) {
                Message mMessage = mMessages.get(position);
                chatViewHolder.setUsername(mMessage.getUsername());
                chatViewHolder.setText(mMessage.getText() + "\n");
            }
        };

        recycler.setAdapter(mAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                mMessages.add(message);
                recycler.scrollToPosition(mMessages.size() - 1);
                mAdapter.notifyItemInserted(mMessages.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.child("chats").child(roomName).addChildEventListener(childEventListener);


        // Send message
        final EditText mMessage = (EditText) findViewById(R.id.message_text);
        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message message = new Message(mMessage.getText().toString(), username);
                ref.child("chats").child(roomName).push().setValue(message);
                mMessage.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
