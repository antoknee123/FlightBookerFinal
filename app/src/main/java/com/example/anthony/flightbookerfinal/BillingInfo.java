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
import android.widget.EditText;
import android.widget.Toast;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class BillingInfo extends AppCompatActivity {
    private EditText firstName, lastName, phone, address, card, expiry;
    private Button submit ;
    Client client;
    DBHelper helper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_info);
        firstName = (EditText)findViewById(R.id.billing_firstName) ;
        lastName = (EditText)findViewById(R.id.biiling_lastName) ;
        phone = (EditText)findViewById(R.id.billing_phone) ;
        address = (EditText)findViewById(R.id.billing_address) ;
        card = (EditText)findViewById(R.id.billing_card) ;
        expiry = (EditText)findViewById(R.id.billing_expiry) ;

        submit = (Button)findViewById(R.id.btnSubmit) ;
        helper = new DBHelper(this);
        client = new Client();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Boolean result = false;
                setClient();
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                final String flightNumber  = sharedpreferences.getString("flightNumber", "not available");
                final String date  = sharedpreferences.getString("date", "not available");
                final String email  = sharedpreferences.getString("email", "not available");
                if(isValidInput(client))
                    result = helper.editProfile(client);
                if(result) {
                    if(helper.insertBooked(email, flightNumber, date )){
                        Toast.makeText(getApplication(),"booked!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), BookedItineraries.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"An error has occurred", Toast.LENGTH_SHORT).show();
                    }


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
    public void setClient(){
        client.setFirstName(firstName.getText().toString());
        client.setLastName(lastName.getText().toString());
        client.setPhone(phone.getText().toString());
        client.setAddress(address.getText().toString());
        client.setCardNumber(card.getText().toString());
        client.setExpiryDate(expiry.getText().toString());

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString("email", "No email found");
        client.setEmail(email);

    }
    public boolean isValidInput(Client client){
        boolean result = true;
        if(client.getFirstName().equals("")||client.getFirstName()==null)
            result = false;
        if(client.getLastName().equals("")||client.getLastName()==null)
            result = false;
        if(client.getPhone().equals("")||client.getPhone()==null)
            result = false;
        if(client.getAddress().equals("")||client.getAddress()==null)
            result = false;
        if(client.getCardNumber().equals("")||client.getCardNumber()==null)
            result = false;
        if(client.getExpiryDate().equals("")||client.getExpiryDate()==null)
            result = false;
        return result;

    }
}
