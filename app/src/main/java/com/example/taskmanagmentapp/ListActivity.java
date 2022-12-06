package com.example.taskmanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.taskmanagmentapp.dataManager.AnimeRecord;
import com.example.taskmanagmentapp.dataManager.AnimeParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    AnimeParser animeParser;

    ListView listView;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.floatingBt);


        animeParser = new AnimeParser(intent.getStringExtra("url"));
        parseTasks();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

    }

    void parseTasks() {
        animeParser.getaPie().getAllTasks().enqueue(new Callback<List<AnimeRecord>>() {
            @Override
            public void onResponse(Call<List<AnimeRecord>> call, Response<List<AnimeRecord>> response) {
                if (response.isSuccessful()) {
                    populateListView(response.body());
                } else {
                    Toast.makeText(ListActivity.this, "Something go wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnimeRecord>> call, Throwable t) {
                Log.e("ListActivity", t.getMessage());
                Toast.makeText(ListActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void populateListView(List<AnimeRecord> animeRecordList) {
        AnimeAdapter animeAdapter = new AnimeAdapter(getApplicationContext(), R.layout.item_list, animeRecordList, animeParser, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                parseTasks();
                return null;
            }
        });
        listView.setAdapter(animeAdapter);
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(ListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add);

        final EditText nameET = dialog.findViewById(R.id.nameET);
        final EditText descriptionET = dialog.findViewById(R.id.descriptionET);
        final SeekBar seekBar = dialog.findViewById(R.id.seekBar);
        final Button button = dialog.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimeRecord animeRecord = new AnimeRecord();
                animeRecord.setName(nameET.getText().toString());
                animeRecord.setDescription(descriptionET.getText().toString());
                animeRecord.setReview(seekBar.getProgress());

                animeParser.getaPie().postTask(animeRecord).enqueue(new Callback<AnimeRecord>() {
                    @Override
                    public void onResponse(Call<AnimeRecord> call, Response<AnimeRecord> response) {
                        if (response.isSuccessful()) {
                            parseTasks();
                            dialog.dismiss();
                        } else {
                            Log.d("Dialog", call.request().toString());
                            Log.d("Dialog", call.request().headers().toString());
                            Log.d("Dialog", call.request().body().toString());
                            Log.d("Dialog", response.message().toString());
                            Log.d("Dialog", Integer.toString(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeRecord> call, Throwable t) {
                        Log.e("Dialog", t.getMessage());
                        Toast.makeText(ListActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        dialog.show();
    }
}