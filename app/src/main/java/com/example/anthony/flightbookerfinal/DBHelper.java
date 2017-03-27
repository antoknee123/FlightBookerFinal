package com.example.anthony.flightbookerfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/*
Group 28
Anthony Kwan
Kushal Parmar
Bill Gray Quitalig
Kartihan Srisaravanapavan
 */

public class  DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CLIENT_TABLE_NAME = "client";
    public static final String BOOKED_TABLE_NAME = "booked";
    public static final String ITINERARY_TABLE_NAME = "itinerary";

    public static final String CLIENT_COLUMN_ID = "id";
    public static final String CLIENT_COLUMN_PASSWORD = "password";
    public static final String CLIENT_COLUMN_FIRSTNAME = "firstname";
    public static final String CLIENT_COLUMN_LASTNAME = "lastname";
    public static final String CLIENT_COLUMN_EMAIL = "email";
    public static final String CLIENT_COLUMN_PHONE = "phone";
    public static final String CLIENT_COLUMN_ADDRESS = "address";
    public static final String CLIENT_COLUMN_CARDNUMBER = "cardNumber";
    public static final String CLIENT_COLUMN_EXPIREDATE = "expiryDate";


    public static final String BOOKED_COLUMN_ID = "id";
    public static final String BOOKED_COLUMN_USEREMAIL = "userEmail";
    public static final String BOOKED_COLUMN_FLIGHTNUMBER = "flightNumber";
    public static final String BOOKED_COLUMN_DATE = "date";
    public static final String BOOKED_COLUMN_TIMESTAMP = "timestamp";

    public static final String ITINERARY_COLUMN_ID = "id";
    public static final String ITINERARY_COLUMN_FLIGHTNUMBER = "flightNumber";
    public static final String ITINERARY_COLUMN_DEPARTURE = "departure";
    public static final String ITINERARY_COLUMN_ARRIVAL = "arrival";
    public static final String ITINERARY_COLUMN_AIRLINE = "airline";
    public static final String ITINERARY_COLUMN_ORIGIN = "origin";
    public static final String ITINERARY_COLUMN_DESTINATION = "destination";
    public static final String ITINERARY_COLUMN_DISTANCE = "distance";
    public static final String ITINERARY_COLUMN_COST = "cost";
    public static final String ITINERARY_COLUMN_DURATION = "duration";


    private static final int DATABASE_VERSION = 5  ;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + CLIENT_TABLE_NAME + "(" + CLIENT_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CLIENT_COLUMN_EMAIL + " TEXT, "
                + CLIENT_COLUMN_PASSWORD + " TEXT, "
                + CLIENT_COLUMN_FIRSTNAME + " TEXT, "
                + CLIENT_COLUMN_LASTNAME + " TEXT, "
                + CLIENT_COLUMN_PHONE + " TEXT, "
                + CLIENT_COLUMN_ADDRESS + " TEXT, "
                + CLIENT_COLUMN_CARDNUMBER + " TEXT, "
                + CLIENT_COLUMN_EXPIREDATE + " TEXT) ";
        String query2 = "CREATE TABLE " + ITINERARY_TABLE_NAME + "(" + ITINERARY_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITINERARY_COLUMN_FLIGHTNUMBER + " TEXT, "
                + ITINERARY_COLUMN_DEPARTURE + " TEXT, "
                + ITINERARY_COLUMN_ARRIVAL + " TEXT, "
                + ITINERARY_COLUMN_AIRLINE + " TEXT, "
                + ITINERARY_COLUMN_ORIGIN + " TEXT, "
                + ITINERARY_COLUMN_DESTINATION + " TEXT, "
                + ITINERARY_COLUMN_COST + " DOUBLE, "
                + ITINERARY_COLUMN_DURATION + " DOUBLE) ";

        String query3 = "CREATE TABLE " + BOOKED_TABLE_NAME + "(" + BOOKED_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOKED_COLUMN_USEREMAIL  + " TEXT, "
                + BOOKED_COLUMN_FLIGHTNUMBER + " TEXT, "
                + BOOKED_COLUMN_DATE + " TEXT, "
                + BOOKED_COLUMN_TIMESTAMP + " TIMESTAMP) ";
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS client");
        db.execSQL("DROP TABLE IF EXISTS itinerary");
        db.execSQL("DROP TABLE IF EXISTS booked");
        onCreate(db);


    }



    public boolean registerClient(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_COLUMN_EMAIL, email);
        contentValues.put(CLIENT_COLUMN_PASSWORD, password);
        return db.insert(CLIENT_TABLE_NAME, null, contentValues) > 0;

    }

    public boolean editProfile(Client client) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_COLUMN_FIRSTNAME, client.getFirstName());
        contentValues.put(CLIENT_COLUMN_LASTNAME, client.getLastName());
        contentValues.put(CLIENT_COLUMN_PHONE, client.getPhone());
        contentValues.put(CLIENT_COLUMN_ADDRESS, client.getAddress());
        contentValues.put(CLIENT_COLUMN_CARDNUMBER, client.getCardNumber());
        contentValues.put(CLIENT_COLUMN_EXPIREDATE, client.getExpiryDate());

        return db.update(CLIENT_TABLE_NAME, contentValues, "email= ?", new String[]{client.getEmail()}) > 0;

    }


    public Cursor getClientData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select*from client where id=" + id + "", null);
        return res;

    }

    public boolean isRegisted(String email, String password) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from client", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String e = res.getString(res.getColumnIndex(CLIENT_COLUMN_EMAIL));
            String p = res.getString(res.getColumnIndex(CLIENT_COLUMN_PASSWORD));
            if (email.equals(e) && password.equals(p)) {
                result = true;
                break;
            }
            res.moveToNext();
        }
        return result;
    }



    public ArrayList<String> getAllClient() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from client", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String fn = res.getString(res.getColumnIndex(CLIENT_COLUMN_FIRSTNAME));
            String ln = res.getString(res.getColumnIndex(CLIENT_COLUMN_LASTNAME));
            arrayList.add(fn + " " + ln);
            res.moveToNext();
        }
        return arrayList;
    }
//get Client information
    public Client getClient(String email) {
       Client client = new Client();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from client where email = \""+ email + "\"", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            client.setEmail(res.getString(res.getColumnIndex(CLIENT_COLUMN_EMAIL)));
            client.setPassword(res.getString(res.getColumnIndex(CLIENT_COLUMN_PASSWORD)));
            client.setFirstName(res.getString(res.getColumnIndex(CLIENT_COLUMN_FIRSTNAME)));
            client.setLastName(res.getString(res.getColumnIndex(CLIENT_COLUMN_LASTNAME)));
            client.setPhone(res.getString(res.getColumnIndex(CLIENT_COLUMN_PASSWORD)));
            client.setAddress(res.getString(res.getColumnIndex(CLIENT_COLUMN_ADDRESS)));
            client.setCardNumber(res.getString(res.getColumnIndex(CLIENT_COLUMN_CARDNUMBER)));
            client.setExpiryDate(res.getString(res.getColumnIndex(CLIENT_COLUMN_EXPIREDATE)));
            res.moveToNext();
        }
        return client;
    }


    //get flight number
    public ArrayList<String> getAllFlightNumber() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from itinerary", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(ITINERARY_COLUMN_ID)) + " " +
                    res.getString(res.getColumnIndex(ITINERARY_COLUMN_FLIGHTNUMBER)));
            res.moveToNext();
        }
        return arrayList;
    }
    //search itinerary
    public ArrayList<String> searchItinerary(String origin, String destination, String orderBy ) {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from itinerary where origin = \"" + origin + "\" and destination = \""
                + destination + "\"" + " order by " + orderBy, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String airline = res.getString(res.getColumnIndex(ITINERARY_COLUMN_AIRLINE));
            double duration = res.getDouble(res.getColumnIndex(ITINERARY_COLUMN_DURATION));
            double cost = res.getDouble(res.getColumnIndex(ITINERARY_COLUMN_COST));
            arrayList.add(airline + "  " + duration + "   " + cost);
            res.moveToNext();
        }
        return arrayList;
    }

    //Get itinerary by origin and destination
    public ArrayList<Itinerary> getItinerary(String origin, String destination, String orderBy) {
        ArrayList<Itinerary> arrayList = new ArrayList<Itinerary>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from itinerary where origin = \"" + origin + "\" and destination = \""
                + destination + "\"" + " order by " + orderBy  , null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Itinerary itinerary = new Itinerary();
            itinerary.setFlightNumner(res.getString(res.getColumnIndex(ITINERARY_COLUMN_FLIGHTNUMBER)));
            itinerary.setDeparture(res.getString(res.getColumnIndex(ITINERARY_COLUMN_DEPARTURE)));
            itinerary.setArrival(res.getString(res.getColumnIndex(ITINERARY_COLUMN_ARRIVAL)));
            itinerary.setAirline(res.getString(res.getColumnIndex(ITINERARY_COLUMN_AIRLINE)));
            itinerary.setOrigin(res.getString(res.getColumnIndex(ITINERARY_COLUMN_ORIGIN)));
            itinerary.setDestination(res.getString(res.getColumnIndex(ITINERARY_COLUMN_DESTINATION)));
            itinerary.setDuration(res.getDouble(res.getColumnIndex(ITINERARY_COLUMN_DURATION)));
            itinerary.setCost(res.getDouble(res.getColumnIndex(ITINERARY_COLUMN_COST)));
            arrayList.add(itinerary);
            res.moveToNext();
        }
        return arrayList;
    }

    public boolean insertItinerary(Itinerary itinerary) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITINERARY_COLUMN_FLIGHTNUMBER, itinerary.getFlightNumner());
        contentValues.put(ITINERARY_COLUMN_DEPARTURE, itinerary.getDeparture());
        contentValues.put(ITINERARY_COLUMN_ARRIVAL, itinerary.getArrival());
        contentValues.put(ITINERARY_COLUMN_AIRLINE, itinerary.getAirline());
        contentValues.put(ITINERARY_COLUMN_ORIGIN, itinerary.getOrigin());
        contentValues.put(ITINERARY_COLUMN_DESTINATION, itinerary.getDestination());
        contentValues.put(ITINERARY_COLUMN_COST, itinerary.getCost());
        contentValues.put(ITINERARY_COLUMN_DURATION, itinerary.getDuration());
        return db.insert(ITINERARY_TABLE_NAME, null, contentValues) > 0;

    }

    public boolean insertBooked(String userEmail, String flightNumber, String date) {
        Date timestamp = new Date();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKED_COLUMN_USEREMAIL, userEmail);
        contentValues.put(BOOKED_COLUMN_FLIGHTNUMBER, flightNumber);
        contentValues.put(BOOKED_COLUMN_DATE, date);
        contentValues.put(BOOKED_COLUMN_TIMESTAMP, timestamp.toString());
        return db.insert(BOOKED_TABLE_NAME, null, contentValues) > 0;

    }

    public ArrayList<String> getBookedList(String userEmail) {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select*from " + BOOKED_TABLE_NAME + " where "+
                BOOKED_COLUMN_USEREMAIL + " = \"" + userEmail +"\"", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            String flightNumber = res.getString(res.getColumnIndex(BOOKED_COLUMN_FLIGHTNUMBER));
            String date = res.getString(res.getColumnIndex(BOOKED_COLUMN_DATE));
            arrayList.add(flightNumber + "   Date: " + date);
            res.moveToNext();
        }
        return arrayList;
    }
    public void setItineraryData() {


        Itinerary itinerary1 = new Itinerary("China Airlines 7501", "8:20am", "11:35am", "China Airlines",
                "Beijing","Taiwan", 208.00, 27);

        Itinerary itinerary2 = new Itinerary("Saudi Air Airlines 60", "12:45pm", "2:25am", "Saudi Arabian Airlines",
                "Toronto", "Addis Ababa", 1624.00 , 2.40);

        Itinerary itinerary3 = new Itinerary("Bonjour Airlines 6418", "11:10am", "3:20pm", "Bonjour Airlines", "Paris" ,
                "Rome", 1223.00, 3.50);

        Itinerary itinerary4 = new Itinerary("Canada Airlines 51", "2:05am", "3:35am", "Canada Airlines", "Toronto",
                "New York", 449.00, 1.30);

        Itinerary itinerary5 = new Itinerary("Korean Air 650", "1:35am", "5:25am", "Korean Air", "Manila",
                "Seoul", 745.00, 3.50);
        insertItinerary(itinerary1);
        insertItinerary(itinerary2);
        insertItinerary(itinerary3);
        insertItinerary(itinerary4);
        insertItinerary(itinerary5);


    }
}





