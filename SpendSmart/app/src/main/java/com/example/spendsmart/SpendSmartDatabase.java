package com.example.spendsmart;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class}, version = 1)
public abstract class SpendSmartDatabase extends RoomDatabase {
    private static SpendSmartDatabase instance;
    public abstract ExpenseDao expenseDao();

    public static synchronized SpendSmartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SpendSmartDatabase.class, "spendsmart_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}