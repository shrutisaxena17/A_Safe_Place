package com.example.asafeplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private TextView login;
    private FirebaseAuth fAuth;
    private EditText editTextname,editTextemail,editTextpassword,editTextage;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth=FirebaseAuth.getInstance();

        login=(TextView) findViewById(R.id.gotologin);
        login.setOnClickListener(this);

        register=(Button) findViewById(R.id.registerbutton);
        register.setOnClickListener(this);

        editTextname=(EditText) findViewById(R.id.fullName);
        editTextage=(EditText) findViewById(R.id.age);
        editTextemail=(EditText) findViewById(R.id.email);
        editTextpassword=(EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gotologin:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.registerbutton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        String fullName=editTextname.getText().toString().trim();
        String age=editTextage.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextname.setError("Full Name is required");
            editTextname.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextage.setError("Age is required");
            editTextage.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide valid email!");
            editTextemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextpassword.setError("Password is required");
            editTextemail.requestFocus();
            return;
        }
        if(password.length() <6){
            editTextpassword.setError("Password length should be atleast 6 characters");
            editTextpassword.requestFocus();
            return;
        }

        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullName,age,email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Register.this, "Failed to register user! Try again!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(Register.this, "Failed to register user! Try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}