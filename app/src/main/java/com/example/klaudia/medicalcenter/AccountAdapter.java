package com.example.klaudia.medicalcenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.klaudia.medicalcenter.DatabaseModel.Account;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private ArrayList<Account> itemList;

    AccountAdapter(ArrayList<Account> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setName(itemList.get(position).getName());
        holder.setValue(itemList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView Name;
        @BindView(R.id.item_value)
        TextView Value;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setName(String name) {
            Name.setText(name);
        }

        public void setValue(String value) {
            Value.setText(value);
        }
    }

}
