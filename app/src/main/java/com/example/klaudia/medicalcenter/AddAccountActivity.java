package com.example.klaudia.medicalcenter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klaudia.medicalcenter.DatabaseModel.Account;
import com.example.klaudia.medicalcenter.DatabaseModel.User;
import com.example.klaudia.medicalcenter.Helper.DatabaseHelper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.tooltip.Tooltip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAccountActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String age;
    private String year, month, day;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "AddAccountActivity";
    @BindView(R.id.nav_image)
    ImageView image;
    @BindView(R.id.ifDonor_checkbox)
    CheckBox ifDonor;
    @BindView(R.id.item_value_medicine)
    Button editMedicine;
    @BindView(R.id.add_account_layout)
    LinearLayout layout;
    @BindView(R.id.add_account_save_btn)
    Button add_account_save_btn;
    @NotEmpty(message = "Pole nie może pozostać puste")
    @Length(min = 3, message = "Imie i nazwisko muszą miec powyzej 3 liter")
    @Pattern(sequence = 2, regex = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ][a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]+", message = "Wprowadz dane w odpowiedniej formie")
    @BindView(R.id.userData)
    EditText userData;

    @BindView(R.id.item_value_illness)
    EditText item_value_illness;
    @BindView(R.id.item_value_alergy)
    EditText item_value_alergy;
    @BindView(R.id.item_value_blood)
    EditText item_value_blood;
    @BindView(R.id.item_value_weight)
    EditText item_value_weight;
    @BindView(R.id.item_value_height)
    EditText item_value_height;
    @BindView(R.id.item_value_notes)
    EditText item_value_notes;
    @BindView(R.id.userAge)
    EditText userAge;
    private boolean ifChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        ButterKnife.bind(this);
        final Validator validator = new Validator(this);
        validator.setValidationListener(this);

        Tooltip tooltipImage = new Tooltip.Builder(image)
                .setText("Dodaj zdjęcie z galerii")
                .setCancelable(true)
                .setBackgroundColor(Color.GRAY)
                .setGravity(Gravity.BOTTOM)
                .build();

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        User user = dbHelper.getUser();
        ifDonor.setChecked(user.isIfDonor());
        Bundle extras= getIntent().getExtras();
        ifChecked = extras.getBoolean("ifDonor");

        ifDonor.setChecked(ifChecked);

        String date = user.getBirthDate();
        userAge.setText(date);
        userData.setText(user.getName() + " " + user.getSurname());

        userAge.setInputType(InputType.TYPE_NULL);
        userAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateDialog();
            }
        });
        userAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    createDateDialog();
                }
            }
        });

        showAccountList(dbHelper.getAllAccount());

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                userAge.setText(date);
            }
        };

        editMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAccountActivity.this, MedicineActivity.class);
                startActivity(intent);
            }
        });

        add_account_save_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operGallery();
            }
        });
    }

    private void showAccountList(ArrayList<Account> itemList) {
        if (itemList.size() > 0) {
            for (Account a : itemList) {
                switch (a.getName()) {
                    case "Choroby":
                        item_value_illness.setText(a.getValue());
                        break;
                    case "Alergie i reakcje":
                        item_value_alergy.setText(a.getValue());
                        break;
                    case "Grupa krwi":
                        item_value_blood.setText(a.getValue());
                        break;
                    case "Waga":
                        item_value_weight.setText(a.getValue());
                        break;
                    case "Wzrost":
                        item_value_height.setText(a.getValue());
                        break;
                    case "Notatki dotyczące zdrowia":
                        item_value_notes.setText(a.getValue());
                        break;
                }
            }
        }
    }

    private void createDateDialog() {
        Calendar cal = Calendar.getInstance();
        age = userAge.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = format.parse(age);
            year = (String) DateFormat.format("yyyy", date);
            month = (String) DateFormat.format("MM", date);
            day = (String) DateFormat.format("dd", date);
        } catch (ParseException e) {
            e.printStackTrace();
            year = String.valueOf(cal.get(Calendar.YEAR));
            month = String.valueOf(cal.get(Calendar.MONTH));
            day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        }

        int yearToCal = Integer.parseInt(year);
        int monthToCal = Integer.parseInt(month)-1;
        int dayToCal = Integer.parseInt(day);

        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(this),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                yearToCal, monthToCal, dayToCal);

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void saveAccount() throws IOException {
        List<Account> accountList = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        User user = dbHelper.getUser();

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);

            if (child instanceof EditText) {
                String childValue = ((EditText) child).getText().toString();

                if (childValue.length() != 0) {
                    String childName = ((TextView) layout.getChildAt(i - 1)).getText().toString();
                    Account account = new Account();
                    account.setName(childName);
                    account.setValue(childValue);
                    accountList.add(account);
                }
            }
        }

        if (accountList.size() != 0) {
            dbHelper.deleteAllAcount();
            for (Account ac : accountList) {
                dbHelper.insertOneAccount(ac);
            }
        }

        if (!(image.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_person_black_24dp).getConstantState()))) {
            ByteArrayOutputStream bytearrayoutputstream;
            bytearrayoutputstream = new ByteArrayOutputStream();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            Bitmap resizedBitmap = getResizedBitmap(bitmap, 100);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bytearrayoutputstream);
            byte[] BYTE = bytearrayoutputstream.toByteArray();
            user.setPicture(BYTE);
        }

        user.setIfDonor(ifDonor.isChecked());
        String userD[] = userData.getText().toString().split(" ");
        user.setName(userD[0]);
        user.setSurname(userD[1]);
        user.setBirthDate(userAge.getText().toString());
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int age = currentYear - Integer.valueOf(user.getBirthDate().substring(user.getBirthDate().length() - 4));
        user.setAge(age);
        dbHelper.updateUser(user);

        Intent intent = new Intent(AddAccountActivity.this, AccountActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean("ifDonor", ifDonor.isChecked());
        intent.putExtras(extras);
        startActivity(intent);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void operGallery() {
        Intent gallery = new Intent(getIntent().ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap resizedBitmap = getResizedBitmap(bitmap, 100);
            image.setImageBitmap(resizedBitmap);
        }
    }

    @Override
    public void onValidationSucceeded() {
        try {
            saveAccount();
        } catch (IOException e) {
            e.printStackTrace();
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