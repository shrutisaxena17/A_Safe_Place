package com.example.asafeplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        emailEditText = (EditText) findViewById(R.id.Forgetemail);
        resetPasswordButton = (Button) findViewById(R.id.resetbutton);
        auth= FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
    String Forgetemail = emailEditText.getText().toString().trim();

    if(Forgetemail.isEmpty()){
        emailEditText.setError("Email is required!");
        emailEditText.requestFocus();
        return;
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(Forgetemail).matches()){
        emailEditText.setError("Please provide valid Email!");
        emailEditText.requestFocus();
        return;
    }
    auth.sendPasswordResetEmail(Forgetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
         if(task.isSuccessful())   {
             Toast.makeText(ForgotActivity.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
         }else{
             Toast.makeText(ForgotActivity.this, "Try Again! There is some error!", Toast.LENGTH_SHORT).show();
         }
        }
    });
    }
}