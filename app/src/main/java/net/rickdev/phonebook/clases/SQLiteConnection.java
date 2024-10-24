package net.rickdev.phonebook.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConnection extends SQLiteOpenHelper {

    final String TABLE_CONTACTS = "CREATE TABLE contacts (" + " id_contact INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " name TEXT, " + " phoneNumber TEXT)";
    public SQLiteConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS contacts");
    onCreate(db);
    }
}
