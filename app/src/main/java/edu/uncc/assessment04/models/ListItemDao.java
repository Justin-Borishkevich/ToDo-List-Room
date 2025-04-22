package edu.uncc.assessment04.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListItemDao {
    @Query("SELECT * from listItems")
    List<ToDoListItem> getAll();

    @Query("SELECT * from listItems WHERE id = :id limit 1")
    User findById(long id);

    @Query("SELECT * from listItems WHERE listId = :listId")
    List<ToDoListItem> findListItems(long listId);

    @Update
    void update(ToDoListItem listItems);

    @Insert
    void insertAll(ToDoListItem... listItems);

    @Delete
    void delete(ToDoListItem... listItems);
}
