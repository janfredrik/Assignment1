package no.clap.assignment1;

public class Weather {
    // Data members
    int     _id;
    String  _wind;
    String  _temp;

    // Empty Constructor
    public Weather() {

    }

    // Constructor
    public Weather(int id, String name, String temp){
        this._id = id;
        this._wind = name;
        this._temp = temp;
    }

    // Constructor
    public Weather(String name, String temp){
        this._wind = name;
        this._temp = temp;
    }

    // Get the ID
    public int getID(){
        return this._id;
    }

    // Get the wind
    public String getWind(){
        return this._wind;
    }

    // Get the temperature
    public String getTemp(){
        return this._temp;
    }
}
