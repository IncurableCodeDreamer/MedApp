package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.aboutApp)
    CardView aboutApp;
    @BindView(R.id.account)
    CardView account;
    @BindView(R.id.calendar)
    CardView calendar;
    @BindView(R.id.medicine)
    CardView medicine;
    @BindView(R.id.hospitals)
    CardView hospitals;
    @BindView(R.id.tutorial)
    CardView tutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(MainActivity.this.getTitle());
        ButterKnife.bind(this);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        if (!dbHelper.doesDatabaseExist(getApplicationContext())) {
            showDialog();
        } else if (dbHelper.getUserCount() == 0) {
            showDialog();
        }
        onMenuOpcionClick();

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);
    }

    private void onMenuOpcionClick() {
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, AboutAppActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, AccountActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, CalendarActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, MedicineActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });

        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, MapsActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectedIntent;
                selectedIntent = new Intent(MainActivity.this, TutorialActivity.class);
                MainActivity.this.startActivity(selectedIntent);
            }
        });
    }

    private void showDialog() {
        DialogStart dialog = new DialogStart();
        dialog.show(getSupportFragmentManager(), "fragment_dialog_start");
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
                MenuSelector.selectedItemDrawer(item, MainActivity.this, drawerLayout);
                return true;
            }
        });
    }
}
