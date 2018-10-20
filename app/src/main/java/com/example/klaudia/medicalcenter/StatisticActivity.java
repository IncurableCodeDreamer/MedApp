package com.example.klaudia.medicalcenter;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        setTitle(StatisticActivity.this.getTitle());
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);
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
                MenuSelector.selectedItemDrawer(item, StatisticActivity.this, drawerLayout);
                return true;
            }
        });
    }
}
