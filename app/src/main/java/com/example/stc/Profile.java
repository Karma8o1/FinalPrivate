package com.example.stc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {
private TextView u_n,dob,city,status;
private DatabaseReference mUsersDatabase;
private String mCurrent_state;

    public Profile()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        u_n = view.findViewById(R.id.username);
        dob = view.findViewById(R.id.dob);
        city = view.findViewById(R.id.city);
        status = view.findViewById(R.id.status);
        mCurrent_state = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_state);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                u_n.setText(snapshot.child("Displayname").getValue().toString());
                dob.setText(snapshot.child("DOB").getValue().toString());
                city.setText(snapshot.child("city").getValue().toString());
                status.setText(snapshot.child("status").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}