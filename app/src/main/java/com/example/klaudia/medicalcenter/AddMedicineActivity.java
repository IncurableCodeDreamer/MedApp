package com.example.klaudia.medicalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicineActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.add_medicine_item_frequency)
    Spinner dropdown;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @Length(min = 3, message = "Nazwa leku musi miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-Z][a-zA-Z ]+", message = "Wprowadz dane w odpowiedniej formie")
    @BindView(R.id.add_medicine_item_name)
    EditText add_medicine_item_name;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @BindView(R.id.add_medicine_item_amount)
    EditText add_medicine_item_amount;
    @BindView(R.id.add_medicine_item_add_info)
    EditText add_medicine_item_add_info;
    @BindView(R.id.add_medicine_btn_activity)
    Button add_medicine_btn;
    List myEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        setTitle(AddMedicineActivity.this.getTitle());
        ButterKnife.bind(this);
        
        List myEntries2;

        try {
            CSVReader reader2 = new CSVReader(new FileReader("leki.csv"));
            List<String[]> myEntries = reader2.readAll();

            File csvfile = new File(Environment.getExternalStorageDirectory() + "/leki.csv");
            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                //myEntries.add(nextLine[1]);
                // nextLine[] is an array of values from the line
                //System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("leki.csv"));
            myEntries2 = reader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        medicine.setName(add_medicine_item_name.getText().toString());
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
        addMedicine();
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
