package no.clap.windeg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     *   Checks if the "checkBox" is checked or not.
     *   Different toast-messages shows according to the status.
     */
    public void isItChecked(View v) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);

        if (cb.isChecked()) {
            Toast.makeText(getApplicationContext(), "It is checked!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "It is not checked!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *  Creates a new Intent (Maps) and starts it when user press the "GPS" button.
     */
    public void startMapActivity(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
}
