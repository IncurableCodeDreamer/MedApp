package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity {
    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tutorial_type)
    Spinner tutorialType;
    @BindView(R.id.tutorial)
    TextView tutorialText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        final String [] description = new String [] {"Pierwsza pomoc","Popażenia","Odmrożenia"};

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);

        String[] items = new String[]{"Pierwsza pomoc", "Popażenia", "Odmrożenia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        tutorialType.setAdapter(adapter);

        tutorialType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               switch (position) {
                   case 0:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[0]);
                       break;
                   case 1:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[1]);
                       break;
                   case 2:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[2]);
                       break;
                   default:
                       tutorialText.setText(description[0]);
                       tutorialText.setVisibility(View.VISIBLE);
                       break;
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            Intent intent = new Intent(TutorialActivity.this, AddAccountActivity.class);
            startActivity(intent);
            return true;
        }
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuSelector.selectedItemDrawer(item, TutorialActivity.this, drawerLayout);
                return true;
            }
        });
    }

}
