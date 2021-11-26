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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
     private TextView register , editTextforgotpass;
     private EditText editTextemail , editTextpassword ;
     private Button signin;
     private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register=(TextView) findViewById(R.id.gotoregister);
        register.setOnClickListener(this);

        signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);

        editTextemail=(EditText) findViewById(R.id.editTextemail);
        editTextpassword=(EditText) findViewById(R.id.editTextpassword);

        editTextforgotpass = (TextView) findViewById(R.id.fogotpassword);
        editTextforgotpass.setOnClickListener(this);

        fAuth= FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.gotoregister:
                  startActivity(new Intent(this,Register.class));
                  break;


              case R.id.signin:
                  userLogin();
                  break;

              case R.id.fogotpassword:
                  startActivity(new Intent(this, ForgotActivity.class));
                  break;

          }
    }

    private void userLogin() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextemail.setError("Email is required!!");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please enter a valid email");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("Password is required!");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextpassword.setError("Minimum password length is 6 characters ");
            editTextpassword.requestFocus();
            return;
        }

        fAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to main page
                        startActivity(new Intent(Login.this , MainActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Check your email to verify your account!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Failed to login! Please check your credentials again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}