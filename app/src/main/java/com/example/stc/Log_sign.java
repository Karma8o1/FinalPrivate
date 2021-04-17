package com.example.stc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_sign extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button signup;
    private Button login;
    private TextView forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign);
        firebaseAuth = FirebaseAuth.getInstance();
        signup = (Button)findViewById(R.id.signin);
        signup.setOnClickListener(this);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        forget = (TextView)findViewById(R.id.forget);
        forget.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }
    public void signup()
    {
        Intent intent = new Intent (Log_sign.this,signup.class);
        startActivity(intent);
    }
    public void login()
    {
        EditText email = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        String emails = email.getText().toString().toLowerCase().trim();
        String pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(emails))
        {
            Toast.makeText(getApplicationContext(),"Email Address Required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(),"Password Required",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emails,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login SuccessFul",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Log_sign.this,Pages.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressDialog.cancel();
                            email.setText("");
                            password.setText("");
                            Toast.makeText(getApplicationContext(),"Login Unsuccessful",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void forget(){
        Toast.makeText(this,"Enter Credentials",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signin:
                signup();
                break;
            case R.id.login:
                login();
                break;
            case R.id.forget:
                forget();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Something is Wrong",Toast.LENGTH_LONG).show();
                break;
        }
    }
}