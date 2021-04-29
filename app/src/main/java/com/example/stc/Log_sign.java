package com.example.stc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log_sign extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button signup;
    private Button login,f_password;
    private TextView forget;
    private CheckBox remember;
    private EditText email,password,fmail;
    private com.google.android.material.textfield.TextInputLayout pass,emails,fmails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign);
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        pass = (com.google.android.material.textfield.TextInputLayout)findViewById(R.id.pass);
        emails = (com.google.android.material.textfield.TextInputLayout)findViewById(R.id.user);
        fmails = (com.google.android.material.textfield.TextInputLayout)findViewById(R.id.f_mail);
        signup = (Button)findViewById(R.id.signin);
        signup.setOnClickListener(this);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
        forget = (TextView)findViewById(R.id.forget);
        forget.setOnClickListener(this);
        remember = (CheckBox)findViewById(R.id.remember);
        fmail = (EditText)findViewById(R.id.fmail);
        f_password = (Button)findViewById(R.id.f_pass);
        remember.setSelected(false);
        f_password.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }
    public void signup()
    {
        Intent intent = new Intent (Log_sign.this,signup.class);
        startActivity(intent);
    }
    public void login()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null && remember.isSelected()==true){
            Toast.makeText(this,"WELCOME BACK",Toast.LENGTH_LONG).show();
            Intent intent = new Intent (Log_sign.this,Pages.class);
            startActivity(intent);
            finish();
        }
        else{

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
    }
    public void forget(){
        signup.setVisibility(signup.GONE);
        login.setVisibility(login.GONE);
        forget.setVisibility(forget.GONE);
        email.setVisibility(email.GONE);
        password.setVisibility(password.GONE);
        pass.setVisibility(pass.GONE);
        emails.setVisibility(emails.GONE);
        remember.setVisibility(remember.GONE);
        fmail.setVisibility(fmail.VISIBLE);
        fmails.setVisibility(fmails.VISIBLE);
        f_password.setVisibility(f_password.VISIBLE);
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
            case R.id.f_pass:
                Toast.makeText(getApplicationContext(),"Reset Code Sent",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Something is Wrong",Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(fmail.getVisibility()==View.VISIBLE)
        {
            signup.setVisibility(signup.VISIBLE);
            login.setVisibility(login.VISIBLE);
            forget.setVisibility(forget.VISIBLE);
            pass.setVisibility(pass.VISIBLE);
            email.setVisibility(email.VISIBLE);
            emails.setVisibility(emails.VISIBLE);
            password.setVisibility(password.VISIBLE);
            remember.setVisibility(remember.VISIBLE);
            fmail.setVisibility(fmail.GONE);
            fmails.setVisibility(fmails.GONE);
            f_password.setVisibility(f_password.GONE);
        }
        else
        super.onBackPressed();
    }
}