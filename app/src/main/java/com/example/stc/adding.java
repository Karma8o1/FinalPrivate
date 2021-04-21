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

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class adding extends Fragment implements View.OnClickListener {
    private EditText deadline, test, batch, ins_name, degree;
    FloatingActionButton upload, cancel;
    private static final String TAG = "adding";
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding, container, false);
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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.upload:

                break;
            case R.id.clear:
                ins_name.setText(null);
                degree.setText(null);
                batch.setText(null);
                deadline.setText(null);
                test.setText(null);
                break;
            case R.id.deadline:
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
                break;
            case R.id.test_date:
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
                break;

        }
    }
}