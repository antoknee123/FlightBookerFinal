package com.example.anthony.flightbookerfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class User extends AppCompatActivity {
    EditText origin, destination, date ;
    Button searchButton;
    DBHelper helper;
    ListView mlist;
    private ArrayAdapter<String> mAdapter;
    String orderBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        origin = (EditText)findViewById(R.id.search_origin);
        destination = (EditText)findViewById(R.id.search_destination);
        date = (EditText)findViewById(R.id.search_date);
        searchButton = (Button)findViewById(R.id.btnSearch) ;
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String ori = origin.getText().toString();
                String des = destination.getText().toString();
                String d = date.getText().toString();
                if(isValid(des, ori, d)){
                    SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("origin", ori);
                    editor.putString("destination", des);
                    editor.putString("date", d);
                    editor.putString("orderBy" , orderBy);
                    editor.commit();

                    // Switching to User screen
                    Intent i = new Intent(getApplicationContext(), PossibleItinerary .class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplication(),"An error has occurred", Toast.LENGTH_SHORT).show();
                }

            }

        });


        Spinner dropdown2 = (Spinner)findViewById(R.id.spinner2);
        String[] items2 = new String[]{"cost", "duration"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                orderBy = (String) parent.getItemAtPosition(position);

                Log.v("item", orderBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent h = new Intent(getApplicationContext(), User.class);
                startActivity(h);
                return true;
            case R.id.action_profile:
                Intent p = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(p);
                return true;
            case R.id.action_booked:
                Intent b = new Intent(getApplicationContext(), BookedItineraries.class);
                startActivity(b);
                return true;
            case R.id.action_logout:
                SharedPreferences.Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                Intent l = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(l);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public boolean isValid(String des, String ori, String d){
        boolean result = true;
        if(des.trim().equals("") ||  des == null )
            result = false;
        if( ori.trim().equals("") ||  ori == null)
            result = false;
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = sdf.parse(d);
            if(date.after(new Date())==false)
                result = date.after(new Date());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result= false;
        }

        return result;
    }
}
