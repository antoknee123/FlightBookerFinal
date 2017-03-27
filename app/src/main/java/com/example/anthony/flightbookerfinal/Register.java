package com.example.anthony.flightbookerfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class Register extends Activity {
    private TextView loginScreen;
    private Button registerButton;
    private EditText email, pass, conPass;
    DBHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        loginScreen = (TextView) findViewById(R.id.link_to_login);
        registerButton = (Button)findViewById(R.id.btnRegister);
        email = (EditText) findViewById(R.id.reg_email);
        pass = (EditText) findViewById(R.id.reg_password);
        conPass = (EditText) findViewById(R.id.reg_conPass);

        helper = new DBHelper(this);
        //validate first


            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Boolean result = false;
                    // insert into database
                    if(isValidRegistration()){
                        result = helper.registerClient(email.getText().toString(), pass.getText().toString());
                    }
                    if(result) {
                        // Switching to Login Screen/closing register screen
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"An error has occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public boolean isValidRegistration(){
        boolean result = false;
        if(isValidEmail(email))
            result = true;
        String p = pass.getText().toString();
        String cp = conPass.getText().toString();
        if(cp.equals(p) && !p.equals("") )
            result=true;
        return result;
    }
    public static boolean isValidEmail(EditText argEditText) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
