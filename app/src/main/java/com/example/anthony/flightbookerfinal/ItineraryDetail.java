package com.example.anthony.flightbookerfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */
public class ItineraryDetail extends AppCompatActivity {
    TextView flightNumber, departure, arrival, airline, origin, destination, duration, cost, test;
    Button btnBook;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_detail);
        helper = new DBHelper(this);

        findView();
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final String ori = sharedpreferences.getString("origin", "not available");
        final String des  = sharedpreferences.getString("destination", "not available");
        final String date  = sharedpreferences.getString("date", "not available");
        final String orderBy  = sharedpreferences.getString("orderBy", "not available");
        final String email  = sharedpreferences.getString("email", "not available");
        ArrayList<Itinerary> list = new ArrayList<Itinerary>();
        list = helper.getItinerary(ori, des, orderBy);
        int position = sharedpreferences.getInt("position", -1);

        Itinerary itinerary = new Itinerary();
        itinerary = list.get(position);
        setDetail(itinerary);
        btnBook.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("flightNumber", flightNumber.getText().toString());
                editor.commit();

                Intent b = new Intent(getApplicationContext(), BillingInfo.class);
                startActivity(b);

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
    private void setDetail(Itinerary itinerary){
        flightNumber.setText(itinerary.getFlightNumner());
        departure.setText(itinerary.getDeparture());
        arrival.setText(itinerary.getArrival());
        airline.setText(itinerary.getAirline());
        origin.setText(itinerary.getOrigin());
        destination.setText(itinerary.getDestination());
        duration.setText(convertToHourMinute(itinerary.getDuration()));
        cost.setText("$ " + String.valueOf(itinerary.getCost()));
    }
   private void findView(){
       flightNumber = (TextView)findViewById(R.id.detail_fNumber);
       departure = (TextView)findViewById(R.id.detail_departure);
       arrival = (TextView)findViewById(R.id.detail_arrival);
       airline = (TextView)findViewById(R.id.detail_airline);
       origin = (TextView)findViewById(R.id.detail_origin);
       destination = (TextView)findViewById(R.id.detail_destination);
       duration = (TextView)findViewById(R.id.detail_duration);
       cost = (TextView)findViewById(R.id.detail_cost);
       btnBook = (Button)findViewById(R.id.btnBookItinerary) ;
   }
    public String convertToHourMinute(double hour){
        String newHour=null;
        int h = (int) Math.floor(hour);
        int m = (int) Math.floor(((hour%h)*60));
        newHour = h + "h" + m +"m";
        return newHour;
    }
}
