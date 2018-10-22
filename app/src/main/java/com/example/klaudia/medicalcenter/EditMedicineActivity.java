package com.example.klaudia.medicalcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;

public class EditMedicineActivity extends AppCompatActivity {

    @BindView(R.id.edit_medicine_item_frequency)
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        String[] items = new String[]{"Dziennie", "Tygodniowo", "Miesięcznie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
