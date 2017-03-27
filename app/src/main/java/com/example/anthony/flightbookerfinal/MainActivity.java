package com.example.anthony.flightbookerfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */
public class MainActivity extends AppCompatActivity {
    Button lauch;
    private EditText email, password;
    private Button userLogin, adminLogin;
    DBHelper helper;

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_pass);
        helper = new DBHelper(this);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        userLogin = (Button) findViewById(R.id.userLogin);
        lauch = (Button) findViewById(R.id.bntLaunch);
        lauch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                helper.setItineraryData();
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = false;

                if ((email.getText().toString().equals("admin")) && (password.getText().toString().equals("admin")))
                    result = true;
                if (result) {
                    // Switching to User screen
                    Intent i = new Intent(getApplicationContext(), Admin.class);
                    startActivity(i);
                } else {
                    if (helper.isRegisted(email.getText().toString(), password.getText().toString()))
                        result = true;
                    if (result) {
                        //session
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("email", email.getText().toString());
                        editor.commit();

                        // Switching to User screen
                        Intent i = new Intent(getApplicationContext(), User.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplication(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });

        // Listening to register new account link
        registerScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });





    }

}
