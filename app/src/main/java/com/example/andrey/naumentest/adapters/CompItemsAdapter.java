package com.example.andrey.naumentest.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrey.naumentest.R;
import com.example.andrey.naumentest.entities.Company;
import com.example.andrey.naumentest.entities.Item;
import com.example.andrey.naumentest.storage.OnListItemClickListener;

import java.util.List;

public class CompItemsAdapter extends RecyclerView.Adapter<CompItemsAdapter.CompItemsHolder> {

    private List<Item> compItems;
    private OnListItemClickListener clickListener;

    public CompItemsAdapter(List<Item> compItems, OnListItemClickListener clickListener) {
        this.compItems = compItems;
        this.clickListener = clickListener;
    }

    @Override
    public CompItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comp_item, parent, false);
        return new CompItemsHolder(v);
    }

    @Override
    public void onBindViewHolder(CompItemsHolder holder, int position) {
        holder.bind(compItems.get(position));
    }

    @Override
    public int getItemCount() {
        return compItems.size();
    }

    public class CompItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView company;
        TextView comText;

        public CompItemsHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            company = (TextView) itemView.findViewById(R.id.company_name_list);
            comText = (TextView) itemView.findViewById(R.id.company_name_text);
        }

        public void bind(Item item) {
            title.setText(item.getName());
            Company com = item.getCompany();
            if(com!=null){
                company.setText(com.getName());
            }else{
                comText.setVisibility(View.GONE);
                company.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

}
