package com.example.chatappss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.widget.Toolbar;

import com.example.chatappss.Adapter.MessageAdapter;
import com.example.chatappss.Model.Chat;
import com.example.chatappss.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;
    EditText entered_message;
    ImageButton send_button;
    Intent intent;
    MessageAdapter messageAdapter;
    List<Chat>  mChat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//
//        getSupportActionBar().setTitle(" ");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        //Text Box ( text entered by user )
        entered_message=findViewById(R.id.entered_message);
        send_button=findViewById(R.id.send_button);

        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = entered_message.getText().toString();
                if(!message.equals(""))
                {
                    sendMessage(fuser.getUid(),userid,message);
                }
                else
                    Toast.makeText(MessageActivity.this,"You cannot send empty text",Toast.LENGTH_LONG).show();
                entered_message.setText("");
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Log.i("MessageActivity",user.getUsername());
                username.setText(user.getUsername());
                if(user.getImageUrl().equals("default"))
                {
                    profile_image.setImageResource(R.drawable.user);
                }
                else{
                    //Picasso.
                }
                readMessage(fuser.getUid(),userid,user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        }
        public void sendMessage(String sender,String receiver,String message)
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);
            reference.child("Chats").push().setValue(hashMap);
        }

        private void readMessage(final String myid, final String userid, final String imageurl)
        {
            Log.i("Message Activity","read Message()");
            mChat=new ArrayList<>();
            reference=FirebaseDatabase.getInstance().getReference("Chats");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mChat.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Chat chat=snapshot.getValue(Chat.class);
                        Log.i("Message Activity mess:",chat.getMessage());
                        Log.i("Message Activity send:",chat.getSender());
                        Log.i("Message Activity rec",chat.getReceiver());
                        Log.i("myid ",myid);
                        Log.i("userid",userid);
                        if((chat.getReceiver().trim().equals(myid) && chat.getSender().trim().equals(userid))||
                                (chat.getReceiver().trim().equals(myid) && chat.getSender().trim().equals(userid)))
                        {
                            mChat.add(chat);
                        }

                        if((chat.getSender().trim()==myid.trim())||(chat.getSender().trim()==userid.trim()))
                        {
                            mChat.add(chat);
                        }


                        messageAdapter=new MessageAdapter(MessageActivity.this,mChat,imageurl);
                        recyclerView.setAdapter(messageAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
