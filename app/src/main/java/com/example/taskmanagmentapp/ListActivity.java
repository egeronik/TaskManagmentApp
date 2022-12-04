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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.taskmanagmentapp.dataManager.PostBody;
import com.example.taskmanagmentapp.dataManager.Task;
import com.example.taskmanagmentapp.dataManager.TaskParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    TaskParser taskParser;

    ListView listView;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.floatingBt);


        taskParser = new TaskParser(intent.getStringExtra("url"));
        parseTasks();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

    }

    void parseTasks(){
        taskParser.getaPie().getAllTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    populateListView(response.body());
                } else {
                    Toast.makeText(ListActivity.this, "Something go wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("ListActivity", t.getMessage());
                Toast.makeText(ListActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void populateListView(List<Task> taskList) {
        TaskAdapter taskAdapter = new TaskAdapter(getApplicationContext(), R.layout.item_list, taskList, taskParser, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                parseTasks();
                return null;
            }
        });
        listView.setAdapter(taskAdapter);
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(ListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_add);

        final EditText textET = dialog.findViewById(R.id.textET);
        final RadioGroup radioGroup = dialog.findViewById(R.id.radio);
        final Button button = dialog.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = radioGroup.getCheckedRadioButtonId();
                if (checked == -1) {
                    Toast.makeText(ListActivity.this, "Select importance", Toast.LENGTH_SHORT).show();
                }else{
                    Task task = new Task();
                    task.setText(textET.getText().toString());
                    switch (checked){
                        case R.id.radioButton0:
                            task.setImportance(0);
                            break;
                        case R.id.radioButton1:
                            task.setImportance(1);
                            break;
                        case R.id.radioButton2:
                            task.setImportance(2);
                            break;

                    }
                    taskParser.getaPie().postTask(task).enqueue(new Callback<Task>() {
                        @Override
                        public void onResponse(Call<Task> call, Response<Task> response) {
                            if(response.isSuccessful()) {
                                parseTasks();
                                dialog.dismiss();
                            }else{
                                Log.d("Dialog",call.request().toString());
                                Log.d("Dialog",call.request().headers().toString());
                                Log.d("Dialog",call.request().body().toString());
                                Log.d("Dialog",response.message().toString());
                                Log.d("Dialog",Integer.toString(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<Task> call, Throwable t) {
                            Log.e("Dialog", t.getMessage());
                            Toast.makeText(ListActivity.this, "RequestError", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        dialog.show();
    }
}