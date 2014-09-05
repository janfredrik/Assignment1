package no.clap.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "weatherLog";

    // Contacts table name
    private static final String TABLE_WEATHER = "weathers";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_WIND = "wind";
    private static final String KEY_TEMP = "temp";      //

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WIND + " TEXT,"
                + KEY_TEMP + " TEXT" + ")";
        db.execSQL(CREATE_WEATHER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);

    // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addWeather(Weather weather) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WIND, weather.getWind()); // Contact Name
        values.put(KEY_TEMP, weather.getTemp()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_WEATHER, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<Weather> getAllWeather() {
        List<Weather> weatherList = new ArrayList<Weather>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Weather weather = new Weather();
                weather.setID(Integer.parseInt(cursor.getString(0)));
                weather.setWind(cursor.getString(1));
                weather.setTemp(cursor.getString(2));
                // Adding contact to list
                weatherList.add(weather);
                } while (cursor.moveToNext());
            }

        // return contact list
        return weatherList;
        }

    // Getting single contact
    public Weather getWeather(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEATHER, new String[] { KEY_ID,
        KEY_WIND, KEY_TEMP }, KEY_ID + "=?",
        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
        cursor.moveToFirst();

        Weather wthr = new Weather(Integer.parseInt(cursor.getString(0)),
        cursor.getString(1), cursor.getString(2));
        // return contact
        return wthr;
    }

    // Getting contacts Count
    public int getWeatherCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WEATHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
        }
}