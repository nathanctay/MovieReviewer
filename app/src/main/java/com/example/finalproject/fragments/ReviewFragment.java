package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.viewmodel.ReviewsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ReviewFragment extends Fragment {

    public ReviewFragment() {
        super(R.layout.review_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewsViewModel viewModel = new ViewModelProvider(getActivity()).get(ReviewsViewModel.class);

        viewModel.getCurrentReview().observe(getViewLifecycleOwner(), review -> {
            if (review == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                TextView reviewTitle = view.findViewById(R.id.review_title);
                reviewTitle.setText(review.title);
                RatingBar reviewRating = view.findViewById(R.id.review_rating);
                reviewRating.setRating(review.rating);
                TextView reviewContent = view.findViewById(R.id.review_content);
                reviewContent.setText(review.review);
                TextView reviewDate = view.findViewById(R.id.review_date);
                reviewDate.setText(review.watchDate);
                ImageView poster = view.findViewById(R.id.review_poster);
                Picasso.get()
                        .load(review.posterInfo)
                        .into(poster);
            }

        });

        view.findViewById(R.id.options_fab).setOnClickListener(v -> {
            FloatingActionButton editFab = view.findViewById(R.id.edit_fab);
            FloatingActionButton deleteFab = view.findViewById(R.id.delete_fab);
            Animation fabMenuOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_menu_open);
            Animation fabMenuClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_menu_close);
            Animation fabOptionOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_option_open);
            Animation fabOptionClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_option_close);
            if (editFab.getVisibility() == View.GONE) {
                editFab.setAnimation(fabOptionOpen);
                editFab.setVisibility(View.VISIBLE);
                deleteFab.setVisibility(View.VISIBLE);
                deleteFab.setAnimation(fabOptionOpen);
                view.findViewById(R.id.options_fab).setAnimation(fabMenuOpen);
            } else {
                editFab.setAnimation(fabOptionClose);
                editFab.setVisibility(View.GONE);
                deleteFab.setAnimation(fabOptionClose);
                deleteFab.setVisibility(View.GONE);
                view.findViewById(R.id.options_fab).setAnimation(fabMenuClose);

            }
        });

        view.findViewById(R.id.delete_fab).setOnClickListener(v -> {
            viewModel.deleteReview();
        });

        view.findViewById(R.id.edit_fab).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, CreateReviewFragment.class, null)
                    .commit();
        });
    }
}
