package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineActivity extends AppCompatActivity {

    @BindView(R.id.add_medicine_item_frequency)
    Spinner dropdown;
    @BindView(R.id.add_medicine_item_name)
    EditText add_medicine_item_name;
    @BindView(R.id.add_medicine_item_amount)
    EditText add_medicine_item_amount;
    @BindView(R.id.add_medicine_item_add_info)
    EditText add_medicine_item_add_info;
    @BindView(R.id.add_medicine_toogle)
    ToggleButton nortification_btn;
    @BindView(R.id.nortification_layout)
    LinearLayout nortifiction_layout;
    @BindView(R.id.add_medicine_btn_activity)
    Button add_medicine_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        setTitle(AddMedicineActivity.this.getTitle());
        ButterKnife.bind(this);

        String[] items = new String[]{"Dziennie", "Tygodniowo", "Miesięcznie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        add_medicine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMedicine();
            }
        });

        nortification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nortification_btn.isChecked()) {
                    nortifiction_layout.setVisibility(View.VISIBLE);
                } else {
                    nortifiction_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addNotification() {

    }

    private void addMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName(add_medicine_item_name.getText().toString());
        medicine.setFrequency(dropdown.getSelectedItem().toString());
        medicine.setAddInfo(add_medicine_item_add_info.getText().toString());
        medicine.setAmount(add_medicine_item_amount.getText().toString());

        if (nortification_btn.isChecked()) {
            addNotification();
        }

        Intent intent = new Intent(AddMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }
}
