package org.dimikatseli.usercoordinates;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.dimikatseli.usercoordinates.db.Coordinate;
import org.dimikatseli.usercoordinates.db.DbHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity {

     boolean not_saved = true;
     private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Bundle bundle = new Bundle();
        dbHelper = new DbHelper(getApplicationContext());

        Button save_button = (Button) findViewById(R.id.save_button);
        Button next_button = (Button) findViewById(R.id.sec_act_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);

        final EditText longitude = (EditText) findViewById(R.id.longitude);
        final EditText latitude = (EditText) findViewById(R.id.latitude);
        final ArrayList<String> timestamps = new ArrayList<>();
        final EditText ID = (EditText) findViewById(R.id.ID);

        //user presses save button
        save_button.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                 if(user_input(longitude,latitude)) {

                     Toast toast = Toast.makeText(
                             getApplicationContext(), "Cordinates Saved", Toast.LENGTH_SHORT);
                     toast.show();
                     not_saved = false;
                     addTimestamp(timestamps);
                     Coordinate coordinate = new Coordinate(Double.valueOf(longitude.getText().toString()),Double.valueOf(latitude.getText().toString()),timestamps.get(timestamps.size()-1));
                     dbHelper.addCoordinate(coordinate);


                 }
                 else{
                     Toast toast = Toast.makeText(
                             getApplicationContext(), "Please insert Cordinates ", Toast.LENGTH_SHORT);
                     toast.show();
                 }
            }
        });





        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(not_saved) {

                    Toast toast = Toast.makeText(
                            getApplicationContext(), "Please save first", Toast.LENGTH_SHORT);
                    toast.show();
                }else{

                    Intent intent =new Intent(getApplicationContext(), SecondActivity.class);
                    //send arraylist of timestamp
                    intent.putStringArrayListExtra("timestampArray",timestamps);
                    startActivity(intent);
                }

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dbHelper.deleteCoordinates(ID.getText().toString());

                    Toast toast = Toast.makeText(
                            getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);
                    toast.show();


            }
        });




    }
    /*
        Find current system time,convert it to date and add it to timestamps ArrayList
        based on Stackoverflow
        https://stackoverflow.com/questions/18929929/convert-timestamp-into-current-date-in-android
     */
    public void addTimestamp(ArrayList timestamps){

        Long tsLong = System.currentTimeMillis()/1000;
        Long ts = tsLong;
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(ts* 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        timestamps.add(date);

    }

    /*
        Check if User has Input in EditText
     */
    public boolean user_input(EditText text1,EditText text2){

        if(text1.getText().toString().isEmpty() || text1.getText().toString().equals("") || text2.getText().toString().isEmpty() || text2.getText().toString().equals("") ) {
            return false;
        }
        else{
            return true;
        }
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }
}
