package com.example.klaudia.medicalcenter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.harpz.androidvalidator.Validator;
import com.harpz.androidvalidator.ValidatorListener;
import com.harpz.androidvalidator.validatorAnnotations.Email;
import com.harpz.androidvalidator.validatorAnnotations.Name;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ValidatorListener {

    private ActionBarDrawerToggle Toggle;
    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    DatabaseHelper dbHelper;

    @Email(message = "Wprowadź email w poprawnej formie", til = R.id.start_dialog_email)
    private EditText userEmail;
    @Name(message = "Wprowadż dane w poprawnej formie", til = R.id.start_dialog_name) //zmiana tekstu wyswietlanego
    private EditText userName;
    @Name(message = "Wprowadż dane w poprawnej formie", til = R.id.start_dialog_surname)
    private EditText userSurname;
    private TextView userBirthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(MainActivity.this.getTitle());
        ButterKnife.bind( this);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        if (!dbHelper.doesDatabaseExist(getApplicationContext())) {
            createUser();
        } else if (dbHelper.getUserCount() == 0){
            createUser();
        }

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);
    }

    private void createUser() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_on_start);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Wprowadzanie swoich danych");

        userBirthdate = dialog.findViewById(R.id.start_dialog_bithdate);
        userName = dialog.findViewById(R.id.start_dialog_name);
        userEmail = dialog.findViewById(R.id.start_dialog_email);
        userSurname = dialog.findViewById(R.id.start_dialog_surname);

        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DAY_OF_MONTH)-1 + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
        userBirthdate.setText(date);

        final Validator validator = new Validator(this);

        Button button = dialog.findViewById(R.id.start_dialog_ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();

                if((validator.equals(true))){
                    dialog.hide(); //zamknij okno dialogowe - dismiss() ??
                }
            }
        });


        dialog.show();

        userBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateDialog();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                userBirthdate.setText(date);
            }
        };
    }

    private void createDateDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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

    @Override
    public void onValidateSuccess() {
        Toast.makeText(this, "Witaj w aplikacji", Toast.LENGTH_SHORT).show();
        final User user = new User();

        user.setBirthDate(userBirthdate.getText().toString());
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int age = currentYear - Integer.valueOf(user.getBirthDate().substring(user.getBirthDate().length() - 4));
                    user.setAge(age);

        user.setName(userName.getText().toString());
        user.setSurname(userSurname.getText().toString());
        user.setEmail(userEmail.getText().toString());
    }

    @Override
    public void onValidateFailed(ArrayList arrayList) {
        Toast.makeText(this, "Wprowadź poprawne dane", Toast.LENGTH_SHORT).show();
    }
}
