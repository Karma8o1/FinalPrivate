package com.example.stc;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class adding extends Fragment implements View.OnClickListener {
    private EditText deadline, test, batch, ins_name, degree;
    FloatingActionButton upload, cancel;
    private static final String TAG = "adding";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference databaseReference;
    String id;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    id = snapshot.child("ins_no").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        upload = view.findViewById(R.id.upload);
        cancel = view.findViewById(R.id.clear);
        ins_name = (EditText) view.findViewById(R.id.ins_name);
        degree = (EditText) view.findViewById(R.id.degree);
        batch = (EditText) view.findViewById(R.id.batch);
        deadline = (EditText) view.findViewById(R.id.deadline);
        test = (EditText) view.findViewById(R.id.test_date);
        deadline.setOnClickListener(this);
        test.setOnClickListener(this);
        upload.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return view;
    }

    public void addinstitute()
    {
        if (!ins_name.getText().toString().isEmpty() || !degree.getText().toString().isEmpty() ||
        !batch.getText().toString().isEmpty() || !deadline.getText().toString().isEmpty() ||!test.getText().toString().isEmpty()) {
            int value = Integer.parseInt(id);
            value = value + 1;
            HashMap<String, String> userMap = new HashMap<>();
            userMap.put("ins_name", ins_name.getText().toString().trim());
            userMap.put("degree", degree.getText().toString().trim());
            userMap.put("batch", batch.getText().toString().trim());
            userMap.put("deadline", deadline.getText().toString().trim());
            userMap.put("test", test.getText().toString().trim());
            userMap.put("logo", "https://www.pngkey.com/png/detail/115-1155670_logo-logo-superior-university-lahore-logo.png");
            userMap.put("available", "yes");
            databaseReference.child("Universities").child(id).setValue(userMap);
            id = String.valueOf(value);
            databaseReference.child("ins_no").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()==true)
                    {
                        Toast.makeText(getView().getContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getView().getContext(),"Upload Failed, Please Check Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else
        {
            Toast.makeText(getView().getContext(),"Please fill the form first", Toast.LENGTH_SHORT).show();
        }
    }
    public void clear()
    {
        ins_name.setText(null);
        degree.setText(null);
        batch.setText(null);
        deadline.setText(null);
        test.setText(null);
    }
    public void deadline()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getView().getContext(), android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy" + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                deadline.setText(date);

            }

        };
    }
    public void testing()
    {
        Calendar cals = Calendar.getInstance();
        int years = cals.get(Calendar.YEAR);
        int months = cals.get(Calendar.MONTH);
        int days = cals.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialogs = new DatePickerDialog(getView().getContext(), android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, years, months, days);
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy" + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                test.setText(date);

            }

        };
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.upload:
                addinstitute();
                clear();
                break;
            case R.id.clear:
                clear();
                break;
            case R.id.deadline:
                deadline();
                break;
            case R.id.test_date:
                testing();
                break;

        }
    }
}