package edu.uncc.assessment04.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ListDao {
    @Query("SELECT * from toDoLists")
    List<ToDoList> getAll();

    @Query("SELECT * from toDoLists WHERE id = :id limit 1")
    User findById(long id);

    @Query("SELECT * from toDoLists WHERE userId = :userId")
    List<ToDoList> findUserLists(long userId);

    @Update
    void update(ToDoList list);

    @Insert
    void insertAll(ToDoList... toDoLists);

    @Delete
    void delete(ToDoList... toDoLists);
}