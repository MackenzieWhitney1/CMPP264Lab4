package com.example.cmpp264lab4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME = "TravelExpertsSqlLite.db";
    private static final int VERSION = 1;

    DBHelper(@Nullable Context context){
        super(context, NAME, null, VERSION);
    }

    // used when opening application and there's no existing database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Agents ( " +
	                "AgentId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
	                "AgtFirstName	VARCHAR(20), " +
                    "AgtMiddleInitial VARCHAR(5)," +
                    "AgtLastName VARCHAR(20), " +
                    "AgtBusPhone VARCHAR(20), " +
                    "AgtEmail VARCHAR(50), " +
                    "AgtPosition VARCHAR(20), " +
                    "AgencyId INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table Agents");
        onCreate(db);
    }
}
