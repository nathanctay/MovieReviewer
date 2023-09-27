package com.example.finalproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.models.Review;
import com.example.finalproject.models.SearchItem;

import java.util.ArrayList;
import android.os.Handler;

public class ReviewsViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<Review> reviews = new ObservableArrayList<>();
    public Handler handler;
    private MutableLiveData<Review> currentReview = new MutableLiveData<>();
    private SearchItem currentSearch = new SearchItem();


    public ReviewsViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        database = Room.databaseBuilder(application, AppDatabase.class, "Reviews").build();
        handler = new Handler();
        new Thread(() -> {
            ArrayList<Review> reviewEntries = (ArrayList<Review>) database.getReviewsDao().getAll();
            handler.post(() -> {
                reviews.addAll(reviewEntries);
            });
        }).start();
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public ObservableArrayList<Review> getReviews() {
        return reviews;
    }

    public MutableLiveData<Review> getCurrentReview() {
        return currentReview;
    }

    public void setCurrentReview(Review review) {
        currentReview.setValue(review);
    }

    public SearchItem getCurrentSearch() {
        return currentSearch;
    }

    public void setCurrentSearch(SearchItem search) {
        currentSearch = search;
    }

    public void saveReview(String title, float rating, String watchDate, String review, String posterInfo, int year) {
        saving.setValue(true);
        new Thread(() -> {
            if (currentReview.getValue() != null) {
                Review current = currentReview.getValue();
                current.title = title;
                current.rating = rating;
                current.watchDate = watchDate;
                current.review = review;
                current.year = year;
                database.getReviewsDao().update(current);
                currentReview.postValue(current);
                int pos = reviews.indexOf(current);
                reviews.set(pos, current);
            } else {
                Review newReview = new Review();
                newReview.title = title;
                newReview.rating = rating;
                newReview.watchDate = watchDate;
                newReview.review = review;
                newReview.posterInfo = posterInfo;
                newReview.year = year;
                newReview.id = database.getReviewsDao().insert(newReview);
                reviews.add(newReview);
            }
            saving.postValue(false);
        }).start();
    }



    public void deleteReview() {
        new Thread(() -> {
            database.getReviewsDao().delete(currentReview.getValue());
            reviews.remove(currentReview.getValue());
            currentReview.postValue(null);
        }).start();
    }
}
