package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.klaudia.medicalcenter.DatabaseModel.Medicine;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.add_medicine_item_frequency)
    Spinner dropdown;
    @BindView(R.id.add_medicine_item_name)
    Spinner add_medicine_item_name;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @BindView(R.id.add_medicine_item_amount)
    EditText add_medicine_item_amount;
    @BindView(R.id.add_medicine_item_add_info)
    EditText add_medicine_item_add_info;
    @BindView(R.id.add_medicine_btn_activity)
    Button add_medicine_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        setTitle(AddMedicineActivity.this.getTitle());
        ButterKnife.bind(this);
        List<String> tokens = new ArrayList<>();

        try {
            InputStream is = getResources().openRawResource(R.raw.rejestrproduktow);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line;
            while((line = reader.readLine()) != null){
               tokens.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapterMedicine = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, tokens);
        add_medicine_item_name.setAdapter(adapterMedicine);

        String[] items = new String[]{"Dziennie", "Tygodniowo", "Miesięcznie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        dropdown.setAdapter(adapter);
        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        add_medicine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    private void addMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName(add_medicine_item_name.getSelectedItem().toString());
        medicine.setFrequency(dropdown.getSelectedItem().toString());
        medicine.setAddInfo(add_medicine_item_add_info.getText().toString());
        medicine.setAmount(add_medicine_item_amount.getText().toString());

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.insertMedicine(medicine);

        Intent intent = new Intent(AddMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Object selItem = add_medicine_item_name.getSelectedItem();
        if(selItem == null){
            Toast.makeText(getApplicationContext(), "Wybierz nazwę leku", Toast.LENGTH_SHORT).show();
        } else if(dbHelper.medicineCheck(add_medicine_item_name.getSelectedItem().toString())) {
            Toast.makeText(getApplicationContext(), "Podany lek już istnieje w apteczce", Toast.LENGTH_SHORT).show();
        } else {
            addMedicine();
        }
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
