package com.example.klaudia.medicalcenter;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.draweLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectedItemDrawer(MenuItem menuItem) {
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                fragmentClass = Account.class;
                break;
            case R.id.nav_examination:
                fragmentClass = Examination.class;
                break;
            case R.id.nav_medicinie:
                fragmentClass = Medicine.class;
                break;
            case R.id.nav_search:
                fragmentClass = Search.class;
                break;
            case R.id.nav_properties:
                fragmentClass = Settings.class;
                break;
            case R.id.nav_statistics:
                fragmentClass = Statistics.class;
                break;
            default:
                fragmentClass = Search.class;
                break;
        }

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedItemDrawer(item);
                return true;
            }
        });
    }
}
