package com.example.finalproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.finalproject.models.Review;

@Database(entities = {Review.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReviewsDao getReviewsDao();
}
