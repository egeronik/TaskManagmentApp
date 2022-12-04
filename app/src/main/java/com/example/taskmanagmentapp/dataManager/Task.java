package com.example.taskmanagmentapp.dataManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("importance")
    @Expose
    Integer importance = 0; //0-1-2
    @SerializedName("text")
    @Expose
    String text = "";
    @SerializedName("checked")
    @Expose
    Boolean checked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", importance=" + importance +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
