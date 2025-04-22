package edu.uncc.assessment04.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "toDoLists")
public class ToDoList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    String name;

    @ColumnInfo
    public long userId;

    public ToDoList() {
    }

    public ToDoList(String name, long userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public long getUserId(){return userId;}
    public long getId(){return id;}

    public void setName(String name) {
        this.name = name;
    }
    public void setUserId(long userId) {this.userId = userId;}
}
