package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.SearchItem;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    SearchItem[] searchItems;
    onSearchClicked listener;

    public interface onSearchClicked {
        void onClick(SearchItem searchItem);
    }
    public SearchAdapter(SearchItem[] searchItems, onSearchClicked listener) {
        this.searchItems = searchItems;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView title = holder.itemView.findViewById(R.id.search_title);
        title.setText(searchItems[position].title);
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(searchItems[position]);
        });
    }

    @Override
    public int getItemCount() {
        return searchItems.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
