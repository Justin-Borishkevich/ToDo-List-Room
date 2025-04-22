package edu.uncc.assessment04.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "listItems")
public class ToDoListItem {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    long listId;
    @ColumnInfo
    long userId;
    @ColumnInfo
    String name;
    @ColumnInfo
    String priority;

    public ToDoListItem() {
    }

    public ToDoListItem(String name, String priority, long listId, long userId) {
        this.name = name;
        this.priority = priority;
        this.listId = listId;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setListId(long listId) {this.listId = listId;}
    public long getListId() {return listId;}

    public void setUserId(long userId) {this.userId = userId;}
    public long getUserId() {return userId;}

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
