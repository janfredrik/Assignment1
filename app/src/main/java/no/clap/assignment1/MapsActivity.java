package no.clap.assignment1;

import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HandleXML obj;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /*
        Gets the location and centers the location and asks another function for the temperature
        based on the lat+long coordinates.
    */
    private void setUpMap() {
      // Getting LocationManager object from System Service LOCATION_SERVICE
      LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

      // Getting Current Location (network is accurate and does not use so much power)
      Location location = lm.getLastKnownLocation("network");

      // Creating a LatLng object for the current location and center the location
      if (location != null) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        findTempInCity(latLng);
      }
      else {
        Toast.makeText(getApplicationContext(), "Could not get your location",
                Toast.LENGTH_SHORT).show();
      }
    }

    /*
        Fetching XML-file from yr.no with the LatLng coordinates. Sets the TextView to
        display this info, and then adds it to the DB and get the other data from DB
     */
    public void findTempInCity(LatLng latLng) {
        TextView tempTextLocation = (TextView) findViewById(R.id.textView_temperature);

        String url = "http://api.yr.no/weatherapi/locationforecast/1.9/?lat=" +
                latLng.latitude +";lon=" + latLng.longitude;

        obj = new HandleXML(url);
        obj.fetchXML();
        while(obj.parsingComplete);
        tempTextLocation.setText(obj.getWind() + " | " + obj.getTemperature() + " °C");
        addWeatherDataToDB(obj.getWind(), obj.getTemperature());
        getWeatherDataFromDB();
    }
        // Great source: http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

    public void addWeatherDataToDB(String wind, String temp) {
      db.addWeather(new Weather(wind, temp));                       // Adds weather to database
    }

    public void getWeatherDataFromDB() {
        int noOfWeathersInDB = db.getWeatherCount()-1;     // -1 because we dont want to get current temp in history
        int numberPrinted = 0;
        TextView last5 = (TextView)findViewById(R.id.last5log);

        last5.setText("Last 5 logs from the database:");

        if (noOfWeathersInDB == 0)
            last5.append("\nNo history yet :(");

        // We only want the latest five, so we start at the latest and go "backwards".
        while (noOfWeathersInDB != 0 && numberPrinted < 5) {
            Weather temporary = db.getWeather(noOfWeathersInDB);    // Get the latest "weather"
            last5.append("\n" + temporary.getWind()  +              // Display the latest weather
                    " | " + temporary.getTemp()+ " °C");

            noOfWeathersInDB--;
            numberPrinted++;
        }
    }
}