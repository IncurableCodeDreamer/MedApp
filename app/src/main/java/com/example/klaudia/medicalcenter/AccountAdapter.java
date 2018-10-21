package com.example.klaudia.medicalcenter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class AccountAdapter extends BaseAdapter {

    DatabaseHelper dbHelper;
    private List<Account> userAccountData;
    //{
//        {
//            put("Choroby", null);
//            put("Leki", null);
//            put("Alergie i reakcje", null);
//            put("Grupa krwi", null);
//            put("Waga", null);
//            put("Wzrost", null);
//            put("BMI", null);
//            put("Notatki dotyczące zdrowia", null);
//        }};

    @Override
    public int getCount() {
        return userAccountData.size();
    }

    @Override
    public Object getItem(int position) {
        return userAccountData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(convertView.getContext());
        convertView = inflater.inflate(R.layout.account_item,null);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemValue = convertView.findViewById(R.id.item_value);
        userAccountData = dbHelper.getAllAccount();

        if(dbHelper.getAccountCount()>0) {
            for(int i=0; i<dbHelper.getAccountCount(); i++){
                itemName.setText(userAccountData.get(i).getName());
                itemValue.setText(userAccountData.get(i).getValue());
            }
        }
        return convertView;
    }
}
