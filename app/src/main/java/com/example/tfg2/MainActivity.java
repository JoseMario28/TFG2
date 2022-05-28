package com.example.tfg2;


import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.tfg2.adapters.AdapterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{

    private TabLayout tablelayout1;
    private ViewPager2 viewpage2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        tablelayout1 = findViewById(R.id.tab_layout);
        viewpage2 = findViewById(R.id.view_page);

        FirebaseUser user = auth.getCurrentUser();

        if(user == null){



        }else{
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        }

        Log.d("peluca", "Usuaro" + auth.getCurrentUser());

        viewpage2.setAdapter(new AdapterFragment(getSupportFragmentManager(),getLifecycle()));
        viewpage2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablelayout1.selectTab(tablelayout1.getTabAt(position));
            }
        });


        tablelayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            viewpage2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

    }


}