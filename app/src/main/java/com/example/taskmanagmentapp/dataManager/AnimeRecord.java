package com.example.taskmanagmentapp.dataManager;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnimeRecord {


    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("review")
    @Expose
    Integer review = 0; //0-1-2
    @SerializedName("description")
    @Expose
    String description = "";
    @SerializedName("name")
    @Expose
    String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "AnimeRecord{" +
                "id=" + id +
                ", review=" + review +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
