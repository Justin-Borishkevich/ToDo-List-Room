package edu.uncc.assessment04.models;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, ToDoList.class, ToDoListItem.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ListDao listDao();
    public abstract ListItemDao listItemDao();





}
