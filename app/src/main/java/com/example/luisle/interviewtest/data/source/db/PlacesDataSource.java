package com.example.luisle.interviewtest.data.source.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.data.Place;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class PlacesDataSource implements IPlacesDataSource {

    private PlacesDbHelper dbHelper;

    @Inject
    public PlacesDataSource(@NonNull Context context) {
        dbHelper = new PlacesDbHelper(context);
    }


    @Override
    public void getPlaces(@NonNull LoadPlacesCallback callback) {
        List<Place> places = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] columns = {
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE
        };

        Cursor cursor = database.query(PlacesPersistenceContract.PlaceEntry.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE));

                Place place = new Place(placeID, placeName, placeAddress, placeImage);
                places.add(place);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        if (places.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onPlacesLoaded(places);
        }
    }

    @Override
    public void getPlace(@NonNull String placeID, @NonNull GetPlaceCallback callback) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] columns = {
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION,
                PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE
        };

        String selection = PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {placeID};

        Cursor cursor = database.query(PlacesPersistenceContract.PlaceEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);


        Place place = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String ID = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID));
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME));
            String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS));
            String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION));
            byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE));

            place = new Place(ID, placeName, placeAddress, placeDescription, placeImage);
        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        if (place == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onPlaceLoaded(place);
        }
    }

    @Override
    public void addPlace(@NonNull Place place) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID, place.getPlaceID());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME, place.getPlaceName());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS, place.getPlaceAddress());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION, place.getPlaceDescription());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE, place.getPlaceImage());

        database.insert(PlacesPersistenceContract.PlaceEntry.TABLE_NAME, null, values);
        database.close();

    }

    @Override
    public void updatePlace(@NonNull Place place) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID, place.getPlaceID());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME, place.getPlaceName());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS, place.getPlaceAddress());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEDESCRIPTION, place.getPlaceDescription());
        values.put(PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE, place.getPlaceImage());

        String whereClause = PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {place.getPlaceID()};

        database.update(PlacesPersistenceContract.PlaceEntry.TABLE_NAME, values, whereClause, whereArgs);
        database.close();
    }

    @Override
    public void deletePlace(@NonNull String placeID) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String whereClause = PlacesPersistenceContract.PlaceEntry.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {placeID};

        database.delete(PlacesPersistenceContract.PlaceEntry.TABLE_NAME, whereClause, whereArgs);
        database.close();
    }

}
