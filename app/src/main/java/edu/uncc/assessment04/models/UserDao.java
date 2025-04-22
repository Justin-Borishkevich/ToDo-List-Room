package edu.uncc.assessment04.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * from users")
    List<User> getAll();

    @Query("SELECT * from users WHERE id = :id limit 1")
    User findById(long id);

    @Update
    void update(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User... user);
}
