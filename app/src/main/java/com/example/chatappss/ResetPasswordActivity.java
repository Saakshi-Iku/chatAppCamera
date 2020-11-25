package com.example.chatappss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText email_tv;
    Button reset_btn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email_tv=findViewById(R.id.email);
        reset_btn=findViewById(R.id.reset_button);
        firebaseAuth=FirebaseAuth.getInstance();
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=email_tv.getText().toString();
                if(email=="")
                {
                    Toast.makeText(ResetPasswordActivity.this,"Email cant be empty ",Toast.LENGTH_SHORT);
                }
                else{

                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPasswordActivity.this,"Plese Check your mail!!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasswordActivity.this,SignIn.class));
                            }else{
                                String error=task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this,error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });


    }
}