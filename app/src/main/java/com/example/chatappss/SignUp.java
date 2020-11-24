package com.example.chatappss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    EditText memail;
    EditText mpassword,musername;
    Button signup;
    String TAG="sign in";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        musername=findViewById(R.id.username);
        signup=findViewById(R.id.signUp);
        //final FirebaseUser currentUser = mAuth.getCurrentUser();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(currentUser!=null){
//                    //no user
//                    Log.i("No ","User");
//                }
//                else{
                    final String email=memail.getText().toString();
                    final String password=mpassword.getText().toString();
                    Log.i(email,password);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userid = user.getUid();
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                        HashMap<String,String> hashMap = new HashMap<>();
                                        hashMap.put("username",musername.getText().toString());
                                        hashMap.put("id",userid);
                                        hashMap.put("imageUrl","default");
                                        hashMap.put("search",musername.toString());
                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }


            //}
        });


    }
}