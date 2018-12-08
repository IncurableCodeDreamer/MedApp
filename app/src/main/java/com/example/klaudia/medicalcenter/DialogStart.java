package com.example.klaudia.medicalcenter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klaudia.medicalcenter.DatabaseModel.User;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class DialogStart extends AppCompatDialogFragment implements Validator.ValidationListener {

    @NotEmpty(message = "Wprowadz email")
    @Email(message = "Wprowadz email w poprawnej")
    @BindView(R.id.start_dialog_email)
    EditText userEmail;
    @NotEmpty(message = "Wprowadz imie")
    @Length(min = 3, message = "Imie musi miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-Z][a-zA-Z ]+", message = "Wprowadz imie w odpowiedniej formie")
    @BindView(R.id.start_dialog_name)
    EditText userName;
    @NotEmpty(message = "Wprowadz nazwisko")
    @Length(min = 3, message = "Nazwisko musi miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-Z][a-zA-Z ]+", message = "Wprowadz nazwisko w odpowiedniej formie")
    @BindView(R.id.start_dialog_surname)
    EditText userSurname;
    @BindView(R.id.start_dialog_bithdate)
    TextView userBirthdate;
    @BindView(R.id.start_dialog_sex)
    RadioGroup userSex;
    Button button;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "DialogStart";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_start, null);
        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        builder.setView(view)
                .setTitle("Wprowadzanie swoich danych");

        userBirthdate = view.findViewById(R.id.start_dialog_bithdate);
        userSex = view.findViewById(R.id.start_dialog_sex);
        userEmail = view.findViewById(R.id.start_dialog_email);
        userName = view.findViewById(R.id.start_dialog_name);
        userSurname = view.findViewById(R.id.start_dialog_surname);
        userBirthdate = view.findViewById(R.id.start_dialog_bithdate);
        userSex = view.findViewById(R.id.start_dialog_sex);
        button = view.findViewById(R.id.start_dialog_ok_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        createUser();
        return builder.create();
    }

    private void createUser() {
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DAY_OF_MONTH) - 1 + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR);
        userBirthdate.setText(date);

        userSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                userSex.check(checkedId);
            }
        });

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

        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(this.getContext()),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this.getContext(), "Witaj w aplikacji", Toast.LENGTH_SHORT).show();
        final User user = new User();

        user.setBirthDate(userBirthdate.getText().toString());
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int age = currentYear - Integer.valueOf(user.getBirthDate().substring(user.getBirthDate().length() - 4));
        user.setAge(age);

        user.setName(userName.getText().toString());
        user.setSurname(userSurname.getText().toString());
        user.setEmail(userEmail.getText().toString());
        RadioButton btn = userSex.findViewById(userSex.getCheckedRadioButtonId());
        user.setSex((String) btn.getText());
        user.setIfDonor(true);

        DatabaseHelper dbHelper = new DatabaseHelper(this.getContext());
        dbHelper.insertUser(user);

        closeDialog();
    }

    private void closeDialog() {
        getDialog().dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this.getContext());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
