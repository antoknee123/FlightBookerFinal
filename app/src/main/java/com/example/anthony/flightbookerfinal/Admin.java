package com.example.anthony.flightbookerfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */
public class Admin extends AppCompatActivity {
    private EditText flightNumber, departure, arrival, airline, origin, destination, cost, duration;
    private Button btnItinerary;
    DBHelper helper;
    Itinerary itinerary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        helper = new DBHelper(this);
        itinerary = new Itinerary();
        flightNumber = (EditText)findViewById(R.id.itinerary_fNumber);
        departure = (EditText)findViewById(R.id.itinerary_departure);
        airline = (EditText)findViewById(R.id.itinerary_airline);
        arrival = (EditText)findViewById(R.id.itinerary_arrival);
        origin = (EditText)findViewById(R.id.itinerary_origin);
        destination = (EditText)findViewById(R.id.itinerary_destination);
        cost = (EditText)findViewById(R.id.itinerary_cost);
        duration = (EditText)findViewById(R.id.itinerary_duration);


        btnItinerary= (Button)findViewById(R.id.btnAddItinerary) ;
        btnItinerary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean result = false;

                if(isEmpty()==false) {
                    setItinerary();
                    result = helper.insertItinerary(itinerary);
                }
                else
                    Toast.makeText(getApplication(),"Please fill out the blanks", Toast.LENGTH_SHORT).show();
                if(result){

                    Toast.makeText(getApplication(),"A record has been inserted ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplication(),"An error has occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_adminHome:
                Intent h = new Intent(getApplicationContext(), Admin.class);
                startActivity(h);
                return true;
            case R.id.action_clientList:
                Intent p = new Intent(getApplicationContext(), ClientList.class);
                startActivity(p);
                return true;
            case R.id.action_itineraryList:
                Intent b = new Intent(getApplicationContext(), ItineraryList.class);
                startActivity(b);
                return true;
            case R.id.action_logout:
                Intent l = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(l);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void setItinerary(){
        itinerary.setFlightNumner(flightNumber.getText().toString());
        itinerary.setDeparture(departure.getText().toString());
        itinerary.setArrival(arrival.getText().toString());
        itinerary.setAirline(airline.getText().toString());
        itinerary.setOrigin(origin.getText().toString());
        itinerary.setDestination(destination.getText().toString());
        if(cost.getText()!= null || cost.getText().length()> 0)
            itinerary.setCost(Double.parseDouble(cost.getText().toString()));
        else
            itinerary.setCost(0.00);
        if(duration.getText()!= null || duration.getText().length()>0)
            itinerary.setDuration(Double.parseDouble(duration.getText().toString()));
        else
            itinerary.setDuration(0.0);

    }
    public boolean isEmpty(){
        boolean result=false;
        if(flightNumber.getText()==null || flightNumber.getText().length()<=0)
            result = true;
        if(departure.getText()==null || departure.getText().length()<=0)
            result = true;
        if(arrival.getText()==null || arrival.getText().length()<=0)
            result = true;
        if(airline.getText()==null || airline.getText().length()<=0)
            result = true;
        if(origin.getText()==null || origin.getText().length()<=0)
            result = true;
        if(destination.getText()==null || destination.getText().length()<=0)
            result = true;
        if(cost.getText()==null || cost.getText().length()<=0)
            result = true;
        if(duration.getText()==null || duration.getText().length()<=0 )
            result = true;

        return result;
    }
}
