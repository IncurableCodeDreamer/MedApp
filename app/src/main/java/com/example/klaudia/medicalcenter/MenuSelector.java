package com.example.klaudia.medicalcenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

class MenuSelector {

    static void selectedItemDrawer(MenuItem menuItem, Context currentClass, DrawerLayout drawerLayout) {
        Intent selectedIntent;
        switch (menuItem.getItemId()) {
            case R.id.nav_main:
                selectedIntent = new Intent(currentClass, MainActivity.class);
                break;
            case R.id.nav_account:
                selectedIntent = new Intent(currentClass, AccountActivity.class);
                break;
            case R.id.nav_examination:
                selectedIntent = new Intent(currentClass, CalendarActivity.class);
                break;
            case R.id.nav_medicine:
                selectedIntent = new Intent(currentClass, MedicineActivity.class);
                break;
            case R.id.nav_search:
                selectedIntent = new Intent(currentClass, MapsActivity.class);
                break;
            case R.id.nav_info:
                selectedIntent = new Intent(currentClass, AboutAppActivity.class);
                break;
            case R.id.nav_tutorial:
                selectedIntent = new Intent(currentClass, TutorialActivity.class);
                break;
            default:
                selectedIntent = new Intent(currentClass, MainActivity.class);
                break;
        }

        currentClass.startActivity(selectedIntent);
        drawerLayout.closeDrawers();
    }
}
