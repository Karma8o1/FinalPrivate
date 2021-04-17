package com.example.stc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pages extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageView imageView;
    private TextView user;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrent_user;
    private String mCurrent_state;
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    NavigationView navigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (TextView)findViewById(R.id.user);
        firebaseAuth = FirebaseAuth.getInstance();
        mCurrent_state = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_state);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            String name =snapshot.child("status").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        setContentView(R.layout.activity_pages);toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.profile);
        navigationView.setNavigationItemSelectedListener(this);

    }
    public void profile()
    {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    public void logout()
    {
        firebaseAuth.signOut();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null){
            Toast.makeText(this,"CHECK YOUR INTERNET CONNECTION",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent (this,Log_sign.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.Universities:
                break;
            case R.id.Rental:
                break;
            case R.id.profile:
                break;
            case R.id.settings:
                break;
            case R.id.logout:
            logout();
                break;
            default:
                System.out.print("None Selected");


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

}