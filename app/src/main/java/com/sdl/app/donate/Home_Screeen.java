package com.sdl.app.donate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Home_Screeen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager_Adapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Button donate, receive;
    Timer timer;
    int currentPage = 0, NUM_PAGES = 6;
    final long DELAY_MS = 500;//delay in milliseconds before image has to slide
    final long PERIOD_MS = 2000; // time in milliseconds between image sliding.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "testing");
        Log.d("TOKEN:", "" + token);
        if (token == null || token == "") {
            Intent intent = new Intent(Home_Screeen.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_home__screeen);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);


            viewPager = (ViewPager) findViewById(R.id.view_pager);
            donate = (Button) findViewById(R.id.button_donate);
            tabLayout = (TabLayout) findViewById(R.id.image_dots);
            tabLayout.setupWithViewPager(viewPager, true);
            adapter = new ViewPager_Adapter(this);
            viewPager.setAdapter(adapter);

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES - 1) {
                        currentPage = 0;
                    }
                    viewPager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);

//            donate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            donate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), food_donate_add.class);
                    startActivity(intent);
                }
            });

//            receive.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), ReceiveActivity.class);
//                    startActivity(intent);
//                }
//            });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home__screeen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(),  MyProfile.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_donate) {
            Intent intent = new Intent(getApplicationContext(), food_donate_add.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_money) {
            Intent intent = new Intent(getApplicationContext(), money_donate.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_receive) {
//            Intent intent = new Intent(getApplicationContext(), ReceiveActivity.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_mydonations) {
            Intent intent = new Intent(getApplicationContext(), com.sdl.app.donate.mydonations.MyDonations.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", "");
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
