package com.example.chatappss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v7.widget.Toolbar;

import com.example.chatappss.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;
    EditText entered_message;
    ImageButton send_button;
    Intent intent;

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

        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);

        //Text Box ( text entered by user )
        entered_message=findViewById(R.id.entered_message);
        send_button=findViewById(R.id.send_button);

        intent=getIntent();
        final String userid=intent.getStringExtra("userid");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageUrl().equals("default"))
                {
                    profile_image.setImageResource(R.drawable.user);
                }
                else{
                    //Picasso.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    }
