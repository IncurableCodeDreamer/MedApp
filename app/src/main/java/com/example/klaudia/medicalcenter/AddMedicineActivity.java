package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        setTitle(AddMedicineActivity.this.getTitle());
        ButterKnife.bind(this);

        String[] items = new String[]{"Dziennie", "Tygodniowo", "Miesięcznie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button add_medicine_btn = findViewById(R.id.add_medicine_btn_activity);
        add_medicine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Medicine medicine = new Medicine();
                medicine.setName(add_medicine_item_name.getText().toString());
                medicine.setFrequency(dropdown.getSelectedItem().toString());
                medicine.setAddInfo(add_medicine_item_add_info.getText().toString());
                medicine.setAmount(add_medicine_item_amount.getText().toString());

                Intent intent = new Intent(AddMedicineActivity.this, MedicineActivity.class);
                startActivity(intent);
            }
        });
    }
}
