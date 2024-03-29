package com.example.lapitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
         mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar );
         setSupportActionBar(mToolbar);
         getSupportActionBar().setTitle( "Lapit Chat" );

         //Tabs
        mViewPager = (ViewPager) findViewById( R.id.main_tabPager );
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager());

        mViewPager.setAdapter( mSectionsPagerAdapter );

        mTabLayout = (TabLayout) findViewById( R.id.main_tabs);
        mTabLayout.setupWithViewPager( mViewPager );






    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }
    }

    private void sendToStart() {
        Intent starIntent = new Intent(MainActivity.this,StarActivity.class);
        startActivity(starIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu( menu );
         getMenuInflater().inflate( R.menu.main_menu,menu );
         return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
         super.onOptionsItemSelected( item );
         if(item.getItemId() == R.id.main_logout_btn);
        {
            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }
        if(item.getItemId() == R.id.main_settings_bnt)
        {
            Intent settingsIntent = new Intent( MainActivity.this,SettingsActivity.class );
            startActivity( settingsIntent );
        }

        return true;
    }
}

