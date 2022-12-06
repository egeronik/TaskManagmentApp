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

import com.example.taskmanagmentapp.dataManager.AnimeRecord;
import com.example.taskmanagmentapp.dataManager.AnimeParser;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimeAdapter extends ArrayAdapter<AnimeRecord> {

    private Context mContext;
    private final LayoutInflater layoutInflater;
    private final AnimeParser animeParser;
    int mResource;
    Callable<Void> f;

    public AnimeAdapter(@NonNull Context context, int resource, @NonNull List<AnimeRecord> objects, AnimeParser parser, Callable<Void> f) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.animeParser = parser;
        this.f = f;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView idTV = convertView.findViewById(R.id.idTV);
        TextView nameTV = convertView.findViewById(R.id.nameTV);
        TextView textTV = convertView.findViewById(R.id.textTV);
        ImageButton deleteBt = convertView.findViewById(R.id.deleteBt);
        TextView reviewTV = convertView.findViewById(R.id.reviewTV);

        AnimeRecord animeRecord = getItem(position);
        Log.d("Adapter", animeRecord.toString());
        Log.d("TaskAdapter", animeRecord.toString());
        idTV.setText(animeRecord.getId().toString());
        nameTV.setText(animeRecord.getName());
        textTV.setText(animeRecord.getDescription());
        reviewTV.setText(animeRecord.getReview().toString());
//        importanceTV.setText(animeRecord.getImportance().toString());

        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animeParser.getaPie().deleteTaskById(animeRecord.getId()).enqueue(new Callback<AnimeRecord>() {
                    @Override
                    public void onResponse(Call<AnimeRecord> call, Response<AnimeRecord> response) {
                        if (response.isSuccessful()) {
                            try {
                                f.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "ResponseError", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnimeRecord> call, Throwable t) {
                        Log.e("Addapter", t.getMessage());
                        Toast.makeText(mContext, "RequestError", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }
}
