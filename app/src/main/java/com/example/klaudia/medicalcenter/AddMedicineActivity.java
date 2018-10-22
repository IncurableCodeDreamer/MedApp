package com.example.klaudia.medicalcenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
    @BindView(R.id.add_medicine_switch)
    Switch nortification_btn;
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

        nortification_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nortifiction_layout.setVisibility(View.VISIBLE);
                } else {
                    nortifiction_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentTitle("Przypomnienie")
                .setContentText("Zabierz lek")
                .setSmallIcon(R.drawable.ic_access_time_black_24dp);

        manager.notify(1, notification.build());
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
