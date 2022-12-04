package com.example.taskmanagmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.confirm_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpConnection(editText.getText().toString());
            }
        });


    }

    private void setUpConnection(String serverUrl) {
        Intent intent = new Intent(this,ListActivity.class);
        intent.putExtra("url",serverUrl);
        startActivity(intent);
    }
}