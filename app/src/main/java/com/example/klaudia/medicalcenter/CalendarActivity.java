package com.example.klaudia.medicalcenter;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    //Button addEventBtn = (Button) findViewById(R.id.addEventToCalendar);
    //Button deleteEventBtn = (Button) findViewById(R.id.deleteEventFromCalendar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle(CalendarActivity.this.getTitle());
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);

//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                //po wybraniu daty pokaz fragment z wydarzeniem, jesli jest jakies, inaczej nic nie rob
//            }
//        });

        //addEventBtn.setOnClickListener(this);
        //deleteEventBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuSelector.selectedItemDrawer(item, CalendarActivity.this, drawerLayout);
                return true;
            }
        });
    }
}
