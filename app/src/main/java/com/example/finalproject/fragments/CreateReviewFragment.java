package com.example.finalproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalproject.R;
import com.example.finalproject.viewmodel.ReviewsViewModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateReviewFragment extends Fragment {
    private boolean previouslySaving = false;
    private String posterInfo;
    public CreateReviewFragment() {
        super(R.layout.create_review_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewsViewModel viewModel = new ViewModelProvider(getActivity()).get(ReviewsViewModel.class);
        viewModel.getCurrentReview().observe(getViewLifecycleOwner(), (review -> {
            if (review != null) {
                EditText titleEditText = view.findViewById(R.id.title_edit_text);
                titleEditText.setText(review.title);
                TextView yearTextView = view.findViewById(R.id.year_text_view);
                yearTextView.setText(review.year + "");
                ImageView poster = view.findViewById(R.id.create_review_poster);
                Picasso.get()
                        .load(review.posterInfo)
                        .into(poster);
                RatingBar ratingBar = view.findViewById(R.id.rating);
                ratingBar.setRating(review.rating);
                TextView dateTextView = view.findViewById(R.id.date_text_view);
                dateTextView.setText(review.watchDate);
                EditText reviewEditText = view.findViewById(R.id.review_edit_text);
                reviewEditText.setText(review.review);
                posterInfo = review.posterInfo;
            }
        }));
        if (viewModel.getCurrentSearch() != null) {
            EditText titleEditText = view.findViewById(R.id.title_edit_text);
            titleEditText.setText(viewModel.getCurrentSearch().title);
            TextView yearTextView = view.findViewById(R.id.year_text_view);
            yearTextView.setText(viewModel.getCurrentSearch().year + "");
            ImageView poster = view.findViewById(R.id.create_review_poster);
            Picasso.get()
                    .load(viewModel.getCurrentSearch().posterURL)
                    .into(poster);
            posterInfo = viewModel.getCurrentSearch().posterURL;
        }
        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) -> {
            if (!previouslySaving && saving) {
                MaterialButton saveButton = view.findViewById(R.id.save);
                saveButton.setEnabled(false);
                saveButton.setText("Saving...");
                previouslySaving = saving;
            } else if (previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        TextView dateTextView = view.findViewById(R.id.date_text_view);
        DatePicker datePicker = view.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateTextView.setText("Date: " + simpleDateFormat.format(calendar.getTime()));
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                dateTextView.setText("Date: " + simpleDateFormat.format(calendar.getTime()));
            }
        });
        dateTextView.setOnClickListener(v -> {
            if (datePicker.getVisibility() == View.GONE){
                datePicker.setVisibility(View.VISIBLE);
            } else {
                datePicker.setVisibility(View.GONE);
            }


        });

        view.findViewById(R.id.save).setOnClickListener(v -> {
            EditText titleEditText = view.findViewById(R.id.title_edit_text);
            String title = titleEditText.getText().toString();
            TextView yearTextView = view.findViewById(R.id.year_text_view);
            int year = Integer.parseInt(yearTextView.getText().toString());
            RatingBar ratingBar = view.findViewById(R.id.rating);
            float rating = ratingBar.getRating();
            String watchDate = dateTextView.getText().toString();

            EditText reviewEditText = view.findViewById(R.id.review_edit_text);
            String review = reviewEditText.getText().toString();
            // pass info to ViewModel
            viewModel.saveReview(title, rating, watchDate, review, posterInfo, year);
        });
    }
}
