package com.example.taskmanagmentapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskmanagmentapp.dataManager.Task;
import com.example.taskmanagmentapp.dataManager.TaskParser;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context mContext;
    private final LayoutInflater layoutInflater;
    private final TaskParser taskParser;
    int mResource;
    Callable<Void> f;

    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects, TaskParser parser, Callable<Void> f) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.taskParser = parser;
        this.f = f;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView idTV = convertView.findViewById(R.id.idTV);
        TextView textTV = convertView.findViewById(R.id.textTV);
        ImageButton deleteBt = convertView.findViewById(R.id.deleteBt);
        TextView importanceTV = convertView.findViewById(R.id.importanceTV);

        Task task = getItem(position);
        Log.d("TaskAdapter",task.toString());
        idTV.setText(task.getId().toString());
        textTV.setText(task.getText());
        importanceTV.setText(task.getImportance().toString());

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskParser.getaPie().deleteTaskById(task.getId()).enqueue(new Callback<Task>() {
                    @Override
                    public void onResponse(Call<Task> call, Response<Task> response) {
                        if(response.isSuccessful()) {
                            try {
                                f.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(mContext, "ResponseError", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Task> call, Throwable t) {
                        Log.e("Addapter", t.getMessage());
                        Toast.makeText(mContext, "RequestError", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }
}
