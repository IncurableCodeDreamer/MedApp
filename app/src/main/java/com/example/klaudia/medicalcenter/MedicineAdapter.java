package com.example.klaudia.medicalcenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineAdapter extends RecyclerSwipeAdapter<MedicineAdapter.SimpleViewHolder> {

    private Context mContext;
    private ArrayList<Medicine> itemList;

    public MedicineAdapter(Context context, ArrayList<Medicine> objects) {
        this.mContext = context;
        this.itemList = objects;
    }

    MedicineAdapter(ArrayList<Medicine> itemList) {
        this.itemList = itemList;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Medicine item = itemList.get(position);

        if(itemList.get(position).getAddInfo() != null) {
            viewHolder.setAddInfo(itemList.get(position).getAddInfo());

        }
        viewHolder.setName(itemList.get(position).getName());
        viewHolder.setFrequency(itemList.get(position).getFrequency());
        viewHolder.setAmount(itemList.get(position).getAmount());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " Click : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(mContext);
                dbHelper.deleteMedicine(viewHolder.Name.getText().toString());
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                itemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemList.size());
                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "Lek " + viewHolder.Name.getText().toString() + " został usunięty", Toast.LENGTH_SHORT).show();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filterList(ArrayList<Medicine> list) {
        itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.Delete)
        TextView Delete;
        @BindView(R.id.Edit)
        TextView Edit;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;

        public SimpleViewHolder(View itemView) {
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