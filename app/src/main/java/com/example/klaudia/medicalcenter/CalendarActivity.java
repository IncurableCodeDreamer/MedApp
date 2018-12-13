package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.klaudia.medicalcenter.DatabaseModel.Examination;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    private ActionBarDrawerToggle Toggle;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.info)
    FloatingActionButton floatInfo;
    private static final int ADD_NOTE = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setTitle(CalendarActivity.this.getTitle());
        ButterKnife.bind(this);

        Toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(Toggle);
        Toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setDrawerContent(navigationView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        floatInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                floatInfo.setVisibility(View.GONE);
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                Calendar date = eventDay.getCalendar();
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                String strdate = formatter.format(date.getTime());

                if (dbHelper.examinationCheck(strdate)) {
                    Examination ex = dbHelper.getExamination(strdate);
                    if (ex.getName() != null) {
                        floatInfo.setVisibility(View.VISIBLE);
                    }
                } else {
                    floatInfo.setVisibility(View.GONE);
                }
            }
        });

        setIconInCalendar();

        Calendar cal = Calendar.getInstance();
        try {
            calendarView.setDate(cal);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    private void showInfo() {
        Intent intent = new Intent(CalendarActivity.this, InfoExaminationActivity.class);
        Bundle extras = new Bundle();
        Calendar cal = calendarView.getSelectedDate();
        extras.putSerializable("date", cal);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void setIconInCalendar() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Examination> examinationList = dbHelper.getAllExamination();
        List<Examination> notes = dbHelper.getAllNotes();
        List<EventDay> events = new ArrayList<>();
        List<Examination> doubleEvents = checkEvents(examinationList);
        List<Examination> deleteFromNotes = checkEvents(notes);

        for (Examination ex: deleteFromNotes){
            notes.remove(ex);
        }

        for (Examination examination: doubleEvents){
            examinationList.remove(examination);
            String dateFromExamination = examination.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            try {
                Date date = formatter.parse(dateFromExamination);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                events.add(new EventDay(cal, R.drawable.two_icons));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        for (Examination e : examinationList) {
            String dateFromExamination = e.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

            try {
                Date date = formatter.parse(dateFromExamination);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                events.add(new EventDay(cal, R.drawable.ic_favorite_black_24dp));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        for (Examination e : notes) {
            String dateFromExamination = e.getDate();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            try {
                Date date = formatter.parse(dateFromExamination);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                events.add(new EventDay(cal, R.drawable.ic_message_black_24dp));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        calendarView.setEvents(events);
    }

    private List<Examination> checkEvents(List<Examination> examinationList) {
        List<Examination> doubleEvents = new ArrayList<>();

        for (Examination exam: examinationList) {
            if(exam.getName()!= null && exam.getNote() != null){
                if(!(exam.getName().isEmpty()) && !(exam.getNote().isEmpty())) {
                    doubleEvents.add(exam);
                }
            }
        }
        return  doubleEvents;
    }

    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        Bundle extras = new Bundle();
        Calendar cal = calendarView.getSelectedDate();
        extras.putSerializable("date", cal);
        intent.putExtras(extras);
        startActivityForResult(intent, ADD_NOTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.examination_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            Intent intent = new Intent(CalendarActivity.this, AddCalendarActivity.class);
            Bundle extras = new Bundle();
            Date date = calendarView.getFirstSelectedDate().getTime();
            extras.putSerializable("date", date);
            intent.putExtras(extras);
            startActivity(intent);
            return true;
        }
    }

    private void setDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                MenuSelector.selectedItemDrawer(item, CalendarActivity.this, drawerLayout);
                return true;
            }
        });
    }
}
