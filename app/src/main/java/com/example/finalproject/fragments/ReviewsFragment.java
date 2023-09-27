package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.ReviewsAdapter;
import com.example.finalproject.models.Review;
import com.example.finalproject.viewmodel.ReviewsViewModel;

public class ReviewsFragment extends Fragment {
    public ReviewsFragment() {
        super(R.layout.reviews_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewsViewModel viewModel = new ViewModelProvider(getActivity()).get(ReviewsViewModel.class);
        ObservableArrayList<Review> reviews = viewModel.getReviews();

        ReviewsAdapter adapter = new ReviewsAdapter(
                reviews,
                review -> {
                    viewModel.setCurrentReview(review);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, ReviewFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                });
        reviews.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
        RecyclerView reviewsRecyclerView = view.findViewById(R.id.review_recycler_view);
        reviewsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        reviewsRecyclerView.setAdapter(adapter);

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            viewModel.setCurrentReview(null);
            viewModel.setCurrentSearch(null);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

    }
}
