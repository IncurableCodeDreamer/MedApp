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

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditMedicineActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.edit_medicine_item_frequency)
    Spinner dropdown;
    @BindView(R.id.edit_medicine_item_name)
    EditText save_medicine_item_name;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @BindView(R.id.edit_medicine_item_amount)
    EditText save_medicine_item_amount;
    @BindView(R.id.edit_medicine_item_add_info)
    EditText save_medicine_item_add_info;
    @BindView(R.id.save_medicine_btn)
    Button save_medicine_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);
        ButterKnife.bind(this);

        String[] items = new String[]{"Dziennie", "Tygodniowo", "Miesięcznie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        dropdown.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        save_medicine_item_name.setText(extras.getString("name"));
        dropdown.setSelection(selection(Objects.requireNonNull(extras.getString("frequency"))));
        save_medicine_item_add_info.setText(extras.getString("addInfo"));
        save_medicine_item_amount.setText(extras.getString("amount"));

        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        save_medicine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    private int selection(String selection) {
        int value = 0;
        switch (selection) {
            case "Dziennie":
                value = 0;
                break;
            case "Tygodniowo":
                value = 1;
                break;
            case "Miesięcznie":
                value = 2;
                break;
        }
        ;
        return value;
    }

    private void saveMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName(save_medicine_item_name.getText().toString());
        medicine.setFrequency(dropdown.getSelectedItem().toString());
        medicine.setAddInfo(save_medicine_item_add_info.getText().toString());
        medicine.setAmount(save_medicine_item_amount.getText().toString());

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.updateMedicine(medicine);

        Intent intent = new Intent(EditMedicineActivity.this, MedicineActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        saveMedicine();
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
