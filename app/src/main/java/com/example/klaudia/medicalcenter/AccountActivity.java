package com.example.klaudia.medicalcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.klaudia.medicalcenter.DatabaseModel.Account;
import com.example.klaudia.medicalcenter.DatabaseModel.User;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;
import com.github.florent37.viewtooltip.ViewTooltip;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    private AccountAdapter adapter;
    private ArrayList<Account> itemList;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.accountList)
    RecyclerView listView;
    @BindView(R.id.ifDonor_text)
    TextView ifDonor;
    @BindView(R.id.userData)
    TextView userData;
    @BindView(R.id.userAge)
    TextView userAge;
    @BindView(R.id.nav_image)
    ImageView image;
    @BindView(R.id.tooltipBtn)
    FloatingActionButton tooltipBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setTitle(AccountActivity.this.getTitle());
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);

        tooltipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tooltips();
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        if (dbHelper.getAccountCount() != 0) {
            itemList = dbHelper.getAllAccount();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            listView.setLayoutManager(layoutManager);
            adapter = new AccountAdapter(itemList);
            listView.setAdapter(adapter);
        }

        User user = dbHelper.getUser();
        userData.setText(user.getName() + " " + user.getSurname());
        String yearsOld = getYearsOld(user.getAge());
        userAge.setText(user.getBirthDate() + " (" + user.getAge() + " " + yearsOld + ")");
        ifIsDonor(user.isIfDonor());

        if (user.getPicture() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPicture(), 0, user.getPicture().length);
            image.setImageBitmap(bitmap);
        }
    }

    private void tooltips() {
        ViewTooltip
                .on(this, findViewById(R.id.edit_account_menu_btn))
                .autoHide(true, 2000)
                .clickToHide(false)
                .align(ViewTooltip.ALIGN.CENTER)
                .position(ViewTooltip.Position.LEFT)
                .text("Edytuj swoje konto")
                .textColor(Color.WHITE)
                .color(getResources().getColor(R.color.colorAccent))
                .corner(40)
                .arrowWidth(15)
                .arrowHeight(25)
                .distanceWithView(0)
                .show();
    }

    private String getYearsOld(int age) {
        String yearsOld;
        if (age <= 4 || (age > 21 && age <= 24) || (age > 31 && age <= 34) || (age > 41 && age <= 44) || (age > 51 && age <= 54) || (age > 61 && age <= 64) || (age > 71 && age <= 74) || (age > 81 && age <= 84) || (age > 91 && age <= 94)) {
            yearsOld = "lata";
        } else {
            yearsOld = "lat";
        }

        return yearsOld;
    }

    private void ifIsDonor(boolean ifChecked) {
        if (ifChecked) {
            ifDonor.setVisibility(View.VISIBLE);
        } else {
            ifDonor.setVisibility(View.INVISIBLE);
        }
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
            Intent intent = new Intent(AccountActivity.this, AddAccountActivity.class);
            Bundle extras = new Bundle();
            extras.putBoolean("ifDonor", (ifDonor.getVisibility()==View.VISIBLE));
            intent.putExtras(extras);
            startActivity(intent);
            return true;
        }
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuSelector.selectedItemDrawer(item, AccountActivity.this, drawerLayout);
                return true;
            }
        });
    }
}