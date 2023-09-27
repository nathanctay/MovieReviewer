package com.example.finalproject.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public float rating;
    @ColumnInfo
    public String review;
    @ColumnInfo
    public String watchDate;
    @ColumnInfo
    public String posterInfo;
    @ColumnInfo
    public int year;






}
