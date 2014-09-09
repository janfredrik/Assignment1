package no.clap.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Great source and good help with the Database handle:
// http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

public class DatabaseHandler extends SQLiteOpenHelper {

    // Static variables
    private static final int DATABASE_VERSION = 1;              // Database version
    private static final String DATABASE_NAME = "weatherLog";   // Database name
    private static final String TABLE_WEATHER = "weathers";     // Table name

    private static final String KEY_ID = "id";                  // Table -
    private static final String KEY_WIND = "wind";              // columns -
    private static final String KEY_TEMP = "temp";              // names

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating table
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);    // Drop older table if existed
        onCreate(db);                                           // Create tables again
    }

    // Adding new weather-row
    public void addWeather(Weather weather) {
        SQLiteDatabase db = this.getWritableDatabase();         // Open database

        ContentValues values = new ContentValues();
        values.put(KEY_WIND, weather.getWind());                // Wind
        values.put(KEY_TEMP, weather.getTemp());                // Temperature

        db.insert(TABLE_WEATHER, null, values);                 // Inserting row
        db.close();                                             // Closing database connection
    }

    // Getting single weather-row
    public Weather getWeather(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEATHER, new String[] { KEY_ID,
        KEY_WIND, KEY_TEMP }, KEY_ID + "=?",
        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
        cursor.moveToFirst();

        Weather weather = new Weather(Integer.parseInt(cursor.getString(0)),
        cursor.getString(1), cursor.getString(2));

        return weather;
    }

    // Getting the numbers of rows in database
    public int getWeatherCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WEATHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}