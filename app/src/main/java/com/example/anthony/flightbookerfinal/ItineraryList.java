package com.example.anthony.flightbookerfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class ItineraryList extends AppCompatActivity {

    DBHelper helper;
    ListView mlist;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itinerary_list);

        mlist =(ListView)findViewById(R.id.itinerary_list) ;
        helper = new DBHelper(this);
        ArrayList<String> taskList;

        taskList = helper.getAllFlightNumber();

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
}
