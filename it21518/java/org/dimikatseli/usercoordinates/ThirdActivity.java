package org.dimikatseli.usercoordinates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.dimikatseli.usercoordinates.db.Coordinate;
import org.dimikatseli.usercoordinates.db.DbHelper;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        DbHelper db = new DbHelper(getApplicationContext());

        String user = getIntent().getStringExtra("userid_txt");
        String timestamp = getIntent().getStringExtra("spinnerTimestamp");
        ArrayList<Coordinate> results = db.getCoordinates(user,timestamp);
        EditText edittext = (EditText)findViewById(R.id.results);
        edittext.setEnabled(false);
        for (int i = 0; i <results.size() ; i++) {
            results.get(i).toString();
            //System.out.println("RESULTS" +results.get(i).toString());
            edittext.setText(results.get(i).toString());
        }
    }
}
