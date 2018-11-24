package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineActivity extends AppCompatActivity{

    private ActionBarDrawerToggle Toggle;

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.medicine_listView)
    RecyclerView listView;
    @BindView(R.id.medicine_search)
    EditText search;
    private MedicineAdapter adapter;
    private ArrayList<Medicine> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        setTitle(MedicineActivity.this.getTitle());
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        if(dbHelper.getAccountCount() != 0) {
            itemList = dbHelper.getAllMedicine();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            listView.setLayoutManager(layoutManager);
            adapter=new MedicineAdapter(this, itemList);
            adapter.setMode(com.daimajia.swipe.util.Attributes.Mode.Single);
            listView.setAdapter(adapter);

            listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.e("RecyclerView", "onScrollStateChanged");
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);
    }

    private void filter(String s) {
        ArrayList<Medicine> list = new ArrayList<>();
            if(s.length() == 0){
                list.addAll(itemList);
            } else{
                for(Medicine m : itemList){
                    if(m.getName().toLowerCase().contains(s.toLowerCase())){
                        list.add(m);
                    }
                }
            }
        adapter.filterList(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.medicine_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            Intent intent = new Intent(MedicineActivity.this, AddMedicineActivity.class);
            startActivity(intent);
            return true;
        }
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuSelector.selectedItemDrawer(item, MedicineActivity.this, drawerLayout);
                return true;
            }
        });
    }
}


