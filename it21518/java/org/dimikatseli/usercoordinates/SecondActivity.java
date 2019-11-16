package org.dimikatseli.usercoordinates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.dimikatseli.usercoordinates.db.DbHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        boolean userInput=false;
        final EditText userid_txt = (EditText) findViewById(R.id.user_id);
        ArrayList timestamps;
        timestamps=getIntent().getStringArrayListExtra("timestampArray");

       final Spinner spinnerTimestamp=findViewById(R.id.timestamp_spinner);
        //receive all timestamps from main activity
        ArrayAdapter <String> adapter = new ArrayAdapter <>(this, android.R.layout.simple_spinner_dropdown_item, timestamps);
       //set the spinners adapter to the previously created one.
        spinnerTimestamp.setAdapter(adapter);

        //Bundle dbBundle = getIntent().getBundleExtra("db");
        final DbHelper db = new DbHelper(getApplicationContext());;
        Button resultButton = (Button) findViewById(R.id.third_act_button);
        //when the user clicks next



        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //if userid is  empty or ""
                    if(!user_input(userid_txt)) {
                        //inform user to add userid
                        Toast toast = Toast.makeText(
                                getApplicationContext(), "Please insert values first", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    //go to third activity
                    else {
                        db.updateAnonymousCoordinate(userid_txt.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                        intent.putExtra("userid_txt",userid_txt.getText().toString());
                        intent.putExtra("spinnerTimestamp",spinnerTimestamp.getSelectedItem().toString());
                        startActivity(intent);
                    }

            }
        });

    }


    public boolean user_input(EditText text1){

        if(text1.getText().toString().isEmpty() || text1.getText().toString().equals("")  ) {
            return false;
        }
        else{
            return true;
        }
    }
}
