package com.example.homework_2_notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        email = findViewById(R.id.email_edittxt);
        password = findViewById(R.id.password_edittxt);
        login = findViewById(R.id.sign_in_btn);
        register = findViewById(R.id.create_new_btn);

        fAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required!.");
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("password is required!.");
                }

                fAuth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Toast.makeText(Register.this,"error!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _password = password.getText().toString();
                fAuth.signInWithEmailAndPassword(_email, _password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = fAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            ManagerDb.getInstance().openDataBase(this);
            Intent intent = new Intent(this, MainActivity.class);
            //  intent.putExtra("user_name", user.getEmail());
            startActivity(intent);
            finish();
        }
    }




}
