package com.example.klaudia.medicalcenter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DialogMedicine extends AppCompatDialogFragment {

    TextView name;
    Button button;
    String frequency;
    String amount;
    String nameTxt;
    String addinfo;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_medicine, null);

        builder.setView(view)
                .setTitle("Informacje o leku");

        frequency = getArguments().getString("frequency");
        amount = getArguments().getString("amount");
        addinfo = getArguments().getString("addInfo");
        nameTxt = getArguments().getString("name");

        button = view.findViewById(R.id.medicine_dialog_ok_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        onViewCreated(view, savedInstanceState);

        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvAddInfo = view.findViewById(R.id.medicine_dialog_addInfo);
        TextView tvAddInfoTxt = view.findViewById(R.id.txt_addInfo);
        TextView tvFreq = view.findViewById(R.id.medicine_dialog_frequency);
        TextView tvAmount = view.findViewById(R.id.medicine_dialog_amount);
        TextView tvName = view.findViewById(R.id.medicine_dialog_name);

        tvAmount.setText(amount);
        tvFreq.setText(frequency);
        tvName.setText(nameTxt);

        if(addinfo != null && !(addinfo.isEmpty())){
            tvAddInfo.setVisibility(View.VISIBLE);
            tvAddInfoTxt.setVisibility(View.VISIBLE);
            tvAddInfo.setText(addinfo);
        }
    }

    private void closeDialog() {
        getDialog().dismiss();
    }

    public static DialogMedicine newInstance(String addInfo, String amount, String frequency, String name) { // boolean notification
        DialogMedicine f = new DialogMedicine();
        Bundle extras = new Bundle();

        extras.putString("addInfo", addInfo);
        extras.putString("amount", amount);
        extras.putString("frequency", frequency);
        extras.putString("name", name);
        f.setArguments(extras);
        return f;
    }
}
