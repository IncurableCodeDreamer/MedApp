package com.example.klaudia.medicalcenter;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoExaminationActivity extends AppCompatActivity implements Validator.ValidationListener {

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
    @BindView(R.id.delete_hospital)
    Button deleteButton;
    @BindView(R.id.edit_hospital)
    Button editButton;
    @BindView(R.id.save_hospital)
    Button saveButton;
    @BindView(R.id.cancel_hospital)
    Button cancelButton;
    @BindView(R.id.infotext)
    TextView infoTxt;
    @BindView(R.id.hospital_hour)
    TimePicker hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_examination);
        ButterKnife.bind(this);

        String[] items = new String[]{"Konsultacja", "Badanie", "Operacja", "Wizyta kontrolna"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        type.setAdapter(adapter);

        getExamination();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editExamination();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteExamination();
            }
        });

    }

    private void deleteExamination() {
        Examination ex = getExaminationFromDb();

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.deleteExamination(ex);

        Intent intent = new Intent(InfoExaminationActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    private void editExamination() {
        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewToCancel();
            }
        });

        createViewToEdit();
    }

    private void viewToCancel() {
        Examination ex = getExaminationFromDb();

        name.setEnabled(false);
        address.setEnabled(false);
        type.setClickable(false);
        type.setEnabled(false);
        decription.setEnabled(false);
        notif.setEnabled(false);
        notif.setClickable(false);
        hour.setClickable(false);
        hour.setEnabled(false);
        hour.setFocusable(false);

        if (ex.getAddInfo() != null && !(ex.getAddInfo().isEmpty())) {
            infoTxt.setVisibility(View.VISIBLE);
            addIfno.setVisibility(View.VISIBLE);
            addIfno.setEnabled(false);
        }
        deleteButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);

        saveButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }

    private void createViewToEdit() {
        Examination ex = getExaminationFromDb();

        name.setEnabled(true);
        address.setEnabled(true);
        type.setEnabled(true);
        type.setClickable(true);
        decription.setEnabled(true);
        notif.setEnabled(true);
        notif.setClickable(true);
        hour.setClickable(true);
        hour.setEnabled(true);
        hour.setFocusable(true);

        if (ex.getAddInfo() != null && !(ex.getAddInfo().isEmpty())) {
            infoTxt.setVisibility(View.VISIBLE);
            addIfno.setVisibility(View.VISIBLE);
            addIfno.setEnabled(true);
        }
        deleteButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);

        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    private void getExamination() {
        Examination ex = getExaminationFromDb();

        type.setEnabled(false);
        hour.setClickable(false);
        type.setEnabled(false);
        hour.setEnabled(false);
        hour.setFocusable(false);

        name.setText(ex.getName());
        address.setText(ex.getAddress());
        type.setSelection(selection(Objects.requireNonNull(ex.getType())));
        decription.setText(ex.getDescription());
        notif.setChecked(ex.isNofi());
        hour.setHour(Integer.parseInt(ex.getHour()));

        if (ex.getAddInfo() != null && !(ex.getAddInfo().isEmpty())) {
            infoTxt.setVisibility(View.VISIBLE);
            addIfno.setVisibility(View.VISIBLE);
            addIfno.setText(ex.getAddInfo());
        }
    }

    private int selection(String selection) {
        int value = 0;
        switch (selection) {
            case "Konsultacja":
                value = 0;
                break;
            case "Badanie":
                value = 1;
                break;
            case "Operacja":
                value = 2;
                break;
            case "Wizyta kontrolna":
                value = 3;
                break;
        }
        ;
        return value;
    }

    private Examination getExaminationFromDb() {
        Bundle exstras = this.getIntent().getExtras();
        Calendar date = (Calendar) exstras.getSerializable("date");
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String strdate = formatter.format(date.getTime());

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        if (dbHelper.examinationCheck(strdate)) {
            return dbHelper.getExamination(strdate);
        }
        return null;
    }

    public void onValidationSucceeded() {
        updateExamination();
    }

    private void updateExamination() {
        Examination ex = new Examination();
        Examination exToUpdate = getExaminationFromDb();

        ex.setId(exToUpdate.getId());
        ex.setDate(exToUpdate.getDate());
        ex.setName(name.getText().toString());
        ex.setAddress(address.getText().toString());
        ex.setType(type.getSelectedItem().toString());
        ex.setNofi(notif.isChecked());
        ex.setAddInfo(addIfno.getText().toString());
        ex.setHour(String.valueOf(hour.getHour()));

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.updateExamination(ex);

        if (ex.isNofi()) {
            Bundle exstras = this.getIntent().getExtras();
            Calendar date = (Calendar) exstras.getSerializable("date");
            addNotification(date.getTime(), ex);
        }

        Intent intent = new Intent(InfoExaminationActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

//    public static void cancelNotification(Context ctx, int notifyId) {
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
//        nMgr.cancel(notifyId);
//    }

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
        Intent notificationIntent = new Intent(InfoExaminationActivity.this, NotificationReciver.class);
        notificationIntent.putExtras(extras);
        PendingIntent broadcast = PendingIntent.getBroadcast(InfoExaminationActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
    }
}
