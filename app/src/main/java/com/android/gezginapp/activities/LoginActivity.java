package com.android.gezginapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.gezginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userEmailEt,userPasswordEt;
    Button registerButton, loginButton;
    String userEmail, userPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailEt = findViewById(R.id.user_email_login_et);
        userPasswordEt = findViewById(R.id.user_password_login_et);
         loginButton= findViewById(R.id.button_login);
         registerButton= findViewById(R.id.button_go_register);

        mAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = userEmailEt.getText().toString().trim();
                gotoRegister(userEmail);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userEmail = userEmailEt.getText().toString().trim();
               userPassword= userPasswordEt.getText().toString().trim();

               if(!userEmail.isEmpty() && !userPassword.isEmpty()){
                   login(userEmail,userPassword);

               } else{
                   Toast.makeText(getApplicationContext(),"E-posta ya da parola boş bırakılamaz", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void login(String userEmail, String userPassword) {
        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    Log.d("Email","signInWithEmailAndPassword:successful");
                }else{
                    Log.w("Email", "signInWithEmailAndPassword:failure");
                }
            }
        });
    }

    private void gotoRegister(String userEmail) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("Email",userEmail);
        startActivity(intent);
    }

}
