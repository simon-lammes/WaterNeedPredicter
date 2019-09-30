package com.example.waterneedpredicter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HumanPersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(HumanPerson humanPerson);

    @Query("SELECT * FROM HumanPerson")
    List<HumanPerson> getAll();

    @Query("SELECT * FROM HumanPerson WHERE id = :id")
    HumanPerson findById(int id);

    @Delete
    void delete(HumanPerson humanPerson);
}
