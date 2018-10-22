package com.example.klaudia.medicalcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddAccountActivity extends AppCompatActivity{

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        CheckBox ifDonor_checkbox= findViewById(R.id.ifDonor_checkbox);
        ifDonor_checkbox.setChecked(AccountActivity.ifChecked);

        Button editMedicine = findViewById(R.id.item_value_medicine);
        editMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAccountActivity.this, MedicineActivity.class);
                startActivity(intent);
            }
        });

        Button add_account_save_btn = findViewById(R.id.add_account_save_btn);
        add_account_save_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                saveAccount();
            }
        });

        ImageView image = findViewById(R.id.nav_image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operGallery();
            }
        });
    }

    private void saveAccount() {
        List<Account> accountList = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.add_account_layout);

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

        CheckBox ifDonor = findViewById(R.id.ifDonor_checkbox);
        if (ifDonor.isChecked()) {
            AccountActivity.ifChecked = true;
        } else {
            AccountActivity.ifChecked = false;
        }

        Intent intent = new Intent(AddAccountActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    private void operGallery() {
        Intent gallery = new Intent(getIntent().ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            ImageView image = findViewById(R.id.nav_image);
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }
}
