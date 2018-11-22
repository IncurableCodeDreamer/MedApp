package com.example.klaudia.medicalcenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private ArrayList<Medicine> itemList;

    MedicineAdapter(ArrayList<Medicine> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_item, null);
        return new MedicineAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        if(itemList.get(position).getAddInfo() != null) {
            holder.setAddInfo(itemList.get(position).getAddInfo());

        }
        holder.setName(itemList.get(position).getName());
        holder.setFrequency(itemList.get(position).getFrequency());
        holder.setAmount(itemList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filterList(ArrayList<Medicine> list){
        itemList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.medicine_item_name)
        TextView Name;
        @BindView(R.id.medicine_item_amount)
        TextView Amount;
        @BindView(R.id.medicine_item_frequency)
        TextView Frequency;
        @BindView(R.id.medicine_item_add_info)
        TextView AddInfo;
        @BindView(R.id.medicine_item_add_info_name)
        TextView AddInfoName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setName(String name) {
            Name.setText(name);
        }

        public void setAmount(String amount) {
            Amount.setText(amount);
        }

        public void setFrequency(String frequency) {
            Frequency.setText(frequency);
        }

        public void setAddInfo(String addInfo) {
            if(addInfo != null && !(addInfo.isEmpty())){
                AddInfoName.setVisibility(View.VISIBLE);
                AddInfo.setVisibility(View.VISIBLE);
            }
            AddInfo.setText(addInfo);
        }
    }

}
