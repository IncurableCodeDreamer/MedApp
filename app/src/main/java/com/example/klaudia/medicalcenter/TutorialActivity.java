package com.example.klaudia.medicalcenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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

        final String [] description = new String []{
                String.valueOf(Html.fromHtml(getResources().getString(R.string.opazenieTermiczne))),
                String.valueOf(Html.fromHtml(getResources().getString(R.string.opazenieChemiczne))),
                String.valueOf(Html.fromHtml(getResources().getString(R.string.porazeniePradem))),
                String.valueOf(Html.fromHtml(getResources().getString(R.string.udarSloneczny))),
                String.valueOf(Html.fromHtml(getResources().getString(R.string.odmrozenie))),
                String.valueOf(Html.fromHtml(getResources().getString(R.string.krwotokZNosa)))
        };

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);

        String[] items = new String[]{
                "Opażenie termiczne",
                "Opażenie chemiczne",
                "Opażenie elektryczne",
                "Opażenie słoneczne",
                "Odmrożenie",
                "Krwotok z nosa"};

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
                   case 3:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[3]);
                       break;
                   case 4:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[4]);
                       break;
                   case 5:
                       tutorialText.setVisibility(View.VISIBLE);
                       tutorialText.setText(description[5]);
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
                MenuSelector.selectedItemDrawer(item, TutorialActivity.this, drawerLayout);
                return true;
            }
        });
    }

}
