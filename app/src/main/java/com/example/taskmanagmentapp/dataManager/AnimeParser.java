package com.example.taskmanagmentapp.dataManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeParser {
    Retrofit retrofit;
    private AnimeInterface aPie;

    public AnimeParser(String serverUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl+"/anime/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aPie = retrofit.create(AnimeInterface.class);
    }

    public AnimeInterface getaPie() {
        return aPie;
    }
}
