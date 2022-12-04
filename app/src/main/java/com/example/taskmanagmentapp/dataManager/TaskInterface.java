package com.example.taskmanagmentapp.dataManager;

import android.provider.ContactsContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskInterface {
    @Headers("ngrok-skip-browser-warning: 696969")
    @GET("all")
    Call<List<Task>> getAllTasks();

    @Headers("ngrok-skip-browser-warning: 696969")
    @GET("{id}")
    Call<Task> getTaskById(@Path("id") Long id);

    @Headers("ngrok-skip-browser-warning: 696969")
    @POST("add")
    Call<Task> postTask(@Body Task task);

    @Headers("ngrok-skip-browser-warning: 696969")
    @DELETE("{id}")
    Call<Task> deleteTaskById(@Path("id") int id);
}
