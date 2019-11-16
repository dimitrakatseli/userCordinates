package org.dimikatseli.usercoordinates.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dimitra on 15/12/2017.
 * This class is made in order to create,update,insert and delete database
 * In order to create the databse I was based on Android SQLite Tutorial
 * https://www.javatpoint.com/android-sqlite-tutorial
 *
 */

public class  DbHelper extends SQLiteOpenHelper implements Serializable {

    public static final String ANONYMOUS_USER = "Anonymous";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "COORDINATES_DATABASE";
    private static final String TABLE_COORDINATES = "COORDINATES";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERID = "userid";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_TIMESTAMP = "dt";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_COORDINATES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERID + " TEXT,"
                + COLUMN_LONGITUDE + " REAL,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_TIMESTAMP + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COORDINATES);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    // code to add the new contact
    public void addCoordinate(Coordinate coordinate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERID, coordinate.getUserid());
        values.put(COLUMN_LATITUDE, coordinate.getLatitude());
        values.put(COLUMN_LONGITUDE, coordinate.getLongitude());
        values.put(COLUMN_TIMESTAMP, coordinate.getTimestamp());

        // Inserting Row
        db.insert(TABLE_COORDINATES, null, values);
    }

    // code to get the single reult
    public ArrayList<Coordinate> getCoordinates(String userid, String timestamp) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = new String[] { COLUMN_ID,
                COLUMN_USERID, COLUMN_LONGITUDE, COLUMN_LATITUDE, COLUMN_TIMESTAMP };
        String selection = COLUMN_USERID + " = ? AND " + COLUMN_TIMESTAMP + " = ?";
        String[] selectArgs = new String[] { userid, timestamp};
        Cursor cursor = db.query(TABLE_COORDINATES,
                projection,
                selection,
                selectArgs,
                null, null, null, null);

        ArrayList<Coordinate> coordinates = new ArrayList<>();

        while(cursor.moveToNext()) {
            Coordinate coordinate = new Coordinate(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );

            coordinates.add(coordinate);
        }
        cursor.close();

        // return contact
        return coordinates;
    }

    public void updateAnonymousCoordinate(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERID, userid);


        String selection = "userid = ?" ;
        String[] selectionArgs = { ANONYMOUS_USER };

        int count = db.update(
                TABLE_COORDINATES,
                values,
                selection,
                selectionArgs);


    }

    public void deleteCoordinates(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COORDINATES, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}
