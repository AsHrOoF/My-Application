package com.example.android.sec_movieapp.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.sec_movieapp.App.FlickerApplicationClass;
import com.example.android.sec_movieapp.Details.Model;

import java.util.ArrayList;
import java.util.List;


public class DataSource {

    private static DataSource instance;
    private SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;


    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
            instance.dataBaseHelper = new DataBaseHelper(FlickerApplicationClass.getFlickerApp()
                    .getApplicationContext());
            instance.open();
        }
        return instance;
    }

    public void open() {

        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
    }


    private ContentValues getContentFromModel(Model Model) { // Put Information into dataBase

        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseHelper.ID,Model.getID());
        contentValues.put(DataBaseHelper.MOVIE_NAME, Model.getTitle());
        contentValues.put(DataBaseHelper.MOVIE_URL, Model.getUrl());
        contentValues.put(DataBaseHelper.MOVIE_TAG, Model.getTag());
        contentValues.put(DataBaseHelper.MOVIE_OVERVIEW,Model.getOverview());
        contentValues.put(DataBaseHelper.MOVIE_VOTE,Model.getVote());
        contentValues.put(DataBaseHelper.MOVIE_RELEASE,Model.getRelease());
        contentValues.put(DataBaseHelper.IS_FAV, Model.isFav() ? 1 : 0);

        return contentValues;
    }

    private Model getModelFromCursor(Cursor cursor) {
        Model Model = new Model();
        Model.setID(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID)));
        Model.setTitle(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_NAME)));
        Model.setUrl(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_URL)));
        Model.setTag(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_TAG)));
        Model.setOverview(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_OVERVIEW)));
        Model.setVote(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_VOTE)));
        Model.setRelease(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MOVIE_RELEASE)));
        Model.setFav(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.IS_FAV)) == 1);

        return Model;
    }

    public void insertList(List<Model> images) {

        for (Model model : images) {

            ContentValues contentValues = getContentFromModel(model);
            long insertId = database.insert(DataBaseHelper.MOVIE_TABLE, null, contentValues);

            Cursor cursor = database.query(DataBaseHelper.MOVIE_TABLE,
                    null, DataBaseHelper.ID + " = " + insertId, null,
                    null, null, null);

            cursor.moveToFirst();

            Model Model = getModelFromCursor(cursor);

            model.setID(Model.getID());

            cursor.close();
        }
    }

    public List<Model> getImagesByname(String name) {

        List<Model> images = new ArrayList<Model>();

        Cursor cursor = database.query(DataBaseHelper.MOVIE_TABLE,
                null, DataBaseHelper.MOVIE_NAME + " = ?", new String[]{name}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model Model1 = getModelFromCursor(cursor);
            images.add(Model1);
            cursor.moveToNext();
        }
        cursor.close();
        return images;
    }

    public void markAsFav(Model Model1) {

        ContentValues contentValues = getContentFromModel(Model1);
        database.update(DataBaseHelper.MOVIE_TABLE, contentValues
                , DataBaseHelper.ID + "=?", new String[]{String.valueOf(Model1.getID())});
    }

    public List<Model> getFav() {

        List<Model> images = new ArrayList<Model>();

        Cursor cursor = database.query(DataBaseHelper.MOVIE_TABLE,
                null, DataBaseHelper.IS_FAV + " = ?", new String[]{"1"}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Model Model1 = getModelFromCursor(cursor);
            images.add(Model1);
            cursor.moveToNext();
        }
        cursor.close();
        return images;
    }

    public void deleteImagesWithTag(){
        database.delete(DataBaseHelper.MOVIE_TABLE , DataBaseHelper.MOVIE_TAG + "=?"
                , new String[]{"popular"});

        database.delete(DataBaseHelper.MOVIE_TABLE , DataBaseHelper.MOVIE_TAG + "=?"
                , new String[]{"top_rated"});

    }
}
