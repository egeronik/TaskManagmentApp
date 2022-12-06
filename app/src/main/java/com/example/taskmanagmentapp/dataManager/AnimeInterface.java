package com.example.taskmanagmentapp.dataManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnimeInterface {
    @Headers("ngrok-skip-browser-warning: 696969")
    @GET("all")
    Call<List<AnimeRecord>> getAllTasks();

    @Headers("ngrok-skip-browser-warning: 696969")
    @GET("{id}")
    Call<AnimeRecord> getTaskById(@Path("id") Long id);

    @Headers("ngrok-skip-browser-warning: 696969")
    @POST("add")
    Call<AnimeRecord> postTask(@Body AnimeRecord animeRecord);

    @Headers("ngrok-skip-browser-warning: 696969")
    @DELETE("{id}")
    Call<AnimeRecord> deleteTaskById(@Path("id") int id);
}
