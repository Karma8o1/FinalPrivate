package com.example.stc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment{
private TextView u_n,dob,city,status,email;
private DatabaseReference mUsersDatabase;
private String mCurrent_state;
private ImageView img;
private Uri filepath;
private FloatingActionButton floatingActionButton;
private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        u_n = view.findViewById(R.id.username);
        dob = view.findViewById(R.id.dob);
        city = view.findViewById(R.id.city);
        email = view.findViewById(R.id.email);
        status = view.findViewById(R.id.status);
        floatingActionButton = view.findViewById(R.id.upload);
        storageReference = FirebaseStorage.getInstance().getReference();
        mCurrent_state = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_state);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                u_n.setText(snapshot.child("Displayname").getValue().toString());
                email.setText(snapshot.child("Email").getValue().toString());
                dob.setText(snapshot.child("DOB").getValue().toString());
                city.setText(snapshot.child("city").getValue().toString());
                status.setText(snapshot.child("status").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        img = view.findViewById(R.id.pp);
        img.setOnClickListener(v -> showFileChosser());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filepath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChosser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Picture"), 1);
    }
}