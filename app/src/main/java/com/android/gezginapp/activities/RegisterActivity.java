package com.android.gezginapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.gezginapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText userEmailEt,userPasswordEt,userPasswordConfirmEt;
    Button registerButton, loginButton;
    String userEmail, userPassword, userPasswordConfirm;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userEmailEt =findViewById(R.id.user_email_register_et);
        userPasswordEt=findViewById(R.id.user_password_register_et);
        userPasswordConfirmEt=findViewById(R.id.user_confirm_email_register_tv);
        registerButton=findViewById(R.id.button_register);

        mAuth=FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {

            String userEmail = userEmailEt.getText().toString().trim();
            String userPassword = userPasswordEt.getText().toString().trim();
            String userPasswordConfirm=  userPasswordConfirmEt.getText().toString().trim();

            @Override
            public void onClick(View v) {
                userEmail = userEmailEt.getText().toString().trim();
                userPassword = userPasswordEt.getText().toString().trim();
                userPasswordConfirm = userPasswordConfirmEt.getText().toString().trim();

                if(!userEmail.isEmpty() && !userPassword.isEmpty() && !userPasswordConfirm.isEmpty()) {
                    register(userEmail, userPassword, userPasswordConfirm);
                } else {
                    Toast.makeText(getApplicationContext(), "Kaydolmak için tüm alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
            }

    private void register(String userEmail, String userPassword, String userPasswordConfirm){

        mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firabaseUser = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"Kayıt işlemi tamamlandır", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthException) {

                    boolean isErrorWeakPassword = ((FirebaseAuthException) e).getErrorCode().equals("ERROR_WEAK_PASSWORD");
                    boolean isErrorInvalidEmail = ((FirebaseAuthException) e).getErrorCode().equals("ERROR_INVALID_EMAIL");
                    boolean isErrorEmailAlreadyInUse = ((FirebaseAuthException) e).getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE");

                    if(isErrorWeakPassword) {
                        Toast.makeText(getApplicationContext(), "Eksik ya da hatalı şifre", Toast.LENGTH_SHORT).show();
                    } else if (isErrorInvalidEmail) {
                        Toast.makeText(getApplicationContext(), "Geçersiz e-posta adresi", Toast.LENGTH_SHORT).show();
                    } else if (isErrorEmailAlreadyInUse) {
                        Toast.makeText(getApplicationContext(), "E-posta adresi zaten kayıtlı", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
