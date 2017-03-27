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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */
public class PossibleItinerary extends AppCompatActivity {
    TextView pOrigin, pDestination, pDate;
    DBHelper helper;
    ListView mlist;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.possible_itinerary);
        pOrigin = (TextView)findViewById(R.id.possible_origin);
        pDestination = (TextView)findViewById(R.id.possible_destination);
        pDate = (TextView)findViewById(R.id.possible_date);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String origin = sharedpreferences.getString("origin", "not available");
        String destination  = sharedpreferences.getString("destination", "not available");
        String date =sharedpreferences.getString("date", "not available");
        String orderBy =sharedpreferences.getString("orderBy", "not available");
        pOrigin.setText(origin);
        pDestination.setText(destination);
        pDate.setText(date);
        mlist =(ListView)findViewById(R.id.possibleItinerary_list) ;
        helper = new DBHelper(this);
        ArrayList<String> taskList;
        taskList = helper.searchItinerary(origin, destination, orderBy);
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.display_itinerary,
                    R.id.txtDItinerary,
                    taskList);
            mlist.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        mlist.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("position", position);
                editor.commit();
                Intent intent = new Intent(PossibleItinerary.this,
                        ItineraryDetail.class);
                startActivity(intent);
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

}
