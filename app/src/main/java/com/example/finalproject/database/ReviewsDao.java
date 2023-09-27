package com.example.finalproject.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.models.Review;

import java.util.List;

@Dao
public interface ReviewsDao {
    @Insert
    long insert(Review review);

    @Query("SELECT * FROM review")
    List<Review> getAll();

    @Update
    void update(Review review);

    @Delete
    void delete(Review review);
}
