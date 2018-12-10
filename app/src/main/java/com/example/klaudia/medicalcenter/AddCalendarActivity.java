package com.example.klaudia.medicalcenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.klaudia.medicalcenter.DatabaseModel.Examination;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCalendarActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty(message = "Pole nie może pozostać puste")
    @Length(min = 3, message = "Nazwa kliniki musi miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-Z][a-zA-Z ]+", message = "Wprowadz dane w odpowiedniej formie")
    @BindView(R.id.hospital_name)
    EditText name;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @Length(min = 5, message = "Adres musi mieć powyżej 5 liter")
    @BindView(R.id.hospital_address)
    EditText address;
    @BindView(R.id.hospital_type)
    Spinner type;
    @BindView(R.id.hospital_add_info)
    EditText addIfno;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @Length(min = 3, message = "Opis musi miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-Z][a-zA-Z ]+", message = "Wprowadz dane w odpowiedniej formie")
    @BindView(R.id.hospital_description)
    EditText decription;
    @BindView(R.id.hospital_notification)
    Switch notif;
    @BindView(R.id.add_hospital)
    Button addButton;
    @BindView(R.id.hospital_hour)
    TimePicker hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        ButterKnife.bind(this);

        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        String[] items = new String[]{"Konsultacja", "Badanie", "Operacja", "Wizyta kontrolna"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        type.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        addExamination();
    }

    private void addExamination() {
        Bundle extras = getIntent().getExtras();
        Date dateStr = (Date) extras.getSerializable("date");
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String strdate = formatter.format(dateStr.getTime());

        Examination examination = new Examination();
        examination.setAddInfo(addIfno.getText().toString());
        examination.setAddress(address.getText().toString());
        examination.setDate(dateStr.toString());
        examination.setDescription(decription.getText().toString());
        examination.setNofi(notif.isChecked());
        examination.setType(type.getSelectedItem().toString());
        examination.setName(name.getText().toString());
        examination.setHour(String.valueOf(hour.getHour()));

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        if (dbHelper.examinationCheck(strdate)) {
            dbHelper.updateExamination(examination);
        } else {
            dbHelper.insertExamination(examination);
        }

        if (examination.isNofi()) {
            addNotification(dateStr, examination);
        }

        Intent intent = new Intent(AddCalendarActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    private void addNotification(Date date, Examination ex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getYear());
        calendar.set(Calendar.DAY_OF_YEAR, date.getDate() - 1);
        calendar.set(Calendar.HOUR, 9);

        Bundle extras = new Bundle();
        extras.putSerializable("hour", ex.getHour());
        extras.putString("name", ex.getName());

        AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        Intent notificationIntent = new Intent(AddCalendarActivity.this, NotificationReciver.class);
        notificationIntent.putExtras(extras);
        PendingIntent broadcast = PendingIntent.getBroadcast(AddCalendarActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

        Toast toast = Toast.makeText(this, "Przypomnienie włączono na dzień " + date.getDay() + "/" + date.getMonth()
                + "/" + date.getYear() + " o godzinie 9 rano", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getApplicationContext());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(getApplicationContext(), "Sprawdź wprowadzone dane", Toast.LENGTH_SHORT).show();
    }
}
