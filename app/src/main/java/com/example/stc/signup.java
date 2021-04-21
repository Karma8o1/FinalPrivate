package com.example.stc;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;
import java.util.HashMap;

public class signup extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private Button register;
    TextView DOB;
    private static final String TAG = "signup";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        register = (Button)findViewById(R.id.register);
        DOB = (TextView) findViewById(R.id.DOB);
        DOB.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void signup(String email,String pass, String username,String dob, String city)
    {
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),"Email Required",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(),"Password Required",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering......");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful())
                    {
                        FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
                        String uid= current_user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                        String device_token = FirebaseInstanceId.getInstance().getToken();

                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Displayname", username);
                        userMap.put("Email", email);
                        userMap.put("DOB", dob);
                        userMap.put("city", city);
                        userMap.put("image", "default");
                        userMap.put("status","default");
                        userMap.put("device_token", device_token);
                        mDatabase.setValue(userMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"User Registered, Please Login",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent (signup.this,Log_sign.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Unable to store data",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Unable to Register",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);
                EditText emails = (EditText)findViewById(R.id.email);
                EditText city = (EditText)findViewById(R.id.city);

                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String email = emails.getText().toString().toLowerCase().trim();
                String dob = DOB.getText().toString().trim();
                String City = city.getText().toString().trim();
                signup(email,pass,user,dob,City);

            case R.id.DOB:
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(signup.this,android.R.style.Theme_Holo_Light_Dialog, mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  month = month+1;
                  Log.d(TAG,"onDateSet: dd/mm/yyyy"+dayOfMonth+"/"+month+"/"+year);
                  String date = dayOfMonth+"/"+month+"/"+year;
                  DOB.setText(date);
                    }
                };
                //DialogFragment datePicker = new DatePickerFragment();
                //datePicker.show(getSupportFragmentManager(),"Date Picker");
            default:
                return;
        }
    }


}