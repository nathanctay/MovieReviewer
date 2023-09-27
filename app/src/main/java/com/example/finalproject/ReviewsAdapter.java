package com.example.finalproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.models.Review;
import com.squareup.picasso.Picasso;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    ObservableArrayList<Review> reviews;
    OnReviewClicked listener;
    public interface OnReviewClicked {
        void onClick(Review review);
    }

    public ReviewsAdapter(ObservableArrayList<Review> reviews, OnReviewClicked listener) {
        this.reviews = reviews;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView poster = holder.itemView.findViewById(R.id.list_item_poster);
        Picasso.get()
                .load(reviews.get(position).posterInfo)
                .into(poster);
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(reviews.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
