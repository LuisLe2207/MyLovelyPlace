package com.example.luisle.interviewtest.data.source.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class PlacesDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyPlace.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";

    public PlacesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + PlacesPersistenceContract.PlaceEntry.TABLE_NAME + "("
                + PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID + TEXT_TYPE + " PRIMARY KEY, "
                + PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME + TEXT_TYPE + " NOT NULL, "
                + PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS + TEXT_TYPE + " NOT NULL, "
                + PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION + TEXT_TYPE + " NOT NULL, "
                + PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE + BLOB_TYPE + " )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
