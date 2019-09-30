package com.example.waterneedpredicter;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {HumanPerson.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract HumanPersonDao humanPersonDao();

    private static MyRoomDatabase INSTANCE;

    static MyRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyRoomDatabase.class, "my_database").build();
        }
        return INSTANCE;
    }
}
