package com.example.taskmanagmentapp.dataManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskParser {
    Retrofit retrofit;
    private TaskInterface aPie;

    public TaskParser(String serverUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl+"/demo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aPie = retrofit.create(TaskInterface.class);
    }

    public TaskInterface getaPie() {
        return aPie;
    }
}
