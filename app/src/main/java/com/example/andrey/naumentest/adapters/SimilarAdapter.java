package com.example.andrey.naumentest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrey.naumentest.R;
import com.example.andrey.naumentest.entities.Item;
import com.example.andrey.naumentest.storage.OnListItemClickListener;

import java.util.List;

public class SimilarAdapter  extends RecyclerView.Adapter<SimilarAdapter.SimilarHolder> {
    private List<Item> compItems;
    private OnListItemClickListener clickListener;

    public SimilarAdapter(List<Item> compItems, OnListItemClickListener clickListener) {
        this.compItems = compItems;
        this.clickListener = clickListener;
    }

    @Override
    public SimilarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_item, parent, false);
        return new SimilarHolder(v);
    }

    @Override
    public void onBindViewHolder(SimilarHolder holder, int position) {
        holder.bind(compItems.get(position));
    }

    @Override
    public int getItemCount() {
        return compItems.size();
    }

    public class SimilarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView similarText;

        public SimilarHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            similarText = (TextView) itemView.findViewById(R.id.similar_text);
        }

        public void bind(Item item) {
            similarText.setText(item.getName());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

}
