package com.example.stc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import androidx.fragment.app.FragmentTransaction;

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
    String status,email;
    NavigationView navigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (TextView)findViewById(R.id.user);
        firebaseAuth = FirebaseAuth.getInstance();
        mCurrent_state = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_state);
        Profile profile = new Profile();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.container,profile);
        transaction2.commit();

        setContentView(R.layout.activity_pages);toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.admins).setVisible(false);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status = snapshot.child("status").getValue().toString();
                if ((snapshot.child("status").getValue().toString()).equals("admin"))
                {
                    menu.findItem(R.id.admins).setVisible(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
            case R.id.Universities:
                uni university = new uni();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,university);
                transaction.commit();
                toolbar.setTitle("UNIVERSITIES");
                break;
            case R.id.Rental:
                rentals rent = new rentals();
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.container,rent);
                transaction1.commit();
                toolbar.setTitle("RENTAL SERVICES");
                break;
            case R.id.profile:
                Profile profile = new Profile();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.container,profile);
                transaction2.commit();
                toolbar.setTitle("PROFILE");
                break;
            case R.id.settings:
                settings set = new settings();
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.container,set);
                transaction3.commit();
                toolbar.setTitle("SETTINGS");
                break;
            case R.id.admin:
                adding add = new adding();
                FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.replace(R.id.container,add);
                transaction4.commit();
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