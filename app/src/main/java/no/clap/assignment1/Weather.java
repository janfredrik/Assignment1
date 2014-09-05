package no.clap.assignment1;


public class Weather {
    //private variables
    int _id;
    String _wind;
    String _temp;

    // Empty constructor
    public Weather() {

    }
    // constructor
    public Weather(int id, String name, String temp){
        this._id = id;
        this._wind = name;
        this._temp = temp;
    }

    // constructor
    public Weather(String name, String temp){
        this._wind = name;
        this._temp = temp;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getWind(){
        return this._wind;
    }

    // setting name
    public void setWind(String wind){
        this._wind = wind;
    }

    // getting phone number
    public String getTemp(){
        return this._temp;
    }

    // setting phone number
    public void setTemp(String temp){
        this._temp = temp;
    }
}
