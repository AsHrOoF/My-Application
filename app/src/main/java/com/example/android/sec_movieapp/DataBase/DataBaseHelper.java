package com.example.android.sec_movieapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MovieApp.db";

    private static final int DATABASE_VERSION = 3;

    public static final String MOVIE_TABLE = "MovieTable";


    public static final String ID = "_id";

    public static final String MOVIE_NAME = "movieName";

    public static final String MOVIE_URL = "movieURL";

    public static final String MOVIE_TAG = "movieTag";

    public static final String MOVIE_OVERVIEW ="movieOverview";

    public static final String MOVIE_VOTE="movie_Vote";

    public static final String MOVIE_RELEASE="movie_release";



    public static final String IS_FAV = "isFav";


    private static final String DATABASE_CREATE = "create table " +

            MOVIE_TABLE + "( " + ID+ " integer primary key , "
            + MOVIE_NAME+ " text not null, "
            + MOVIE_URL + " text not null,"
            + MOVIE_TAG + " integer, "
            + MOVIE_OVERVIEW + " text not null,"
            + MOVIE_VOTE + " text not null,"
            + MOVIE_RELEASE + " text not null,"
            + IS_FAV    + " integer"
            +");";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);

        onCreate(sqLiteDatabase);
    }
}
