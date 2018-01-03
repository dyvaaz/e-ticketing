package com.diffa.eticket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OutputScreen extends AppCompatActivity {

    TextView output;
    Button logout, back;

    Bundle extras;
    SQLiteDatabase db;

    ComputeMovie movie;
    int position, userID;
    String userIDString, title, classType, time, totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_screen);

        output = (TextView)findViewById(R.id.output);

        extras = getIntent().getExtras();
        title = extras.getString("title");
        classType = extras.getString("class");
        time = extras.getString("time");
        String tickets = extras.getString("tickets");
        userIDString = extras.getString("userID");
        userID = Integer.parseInt(userIDString);
        position = extras.getInt("position");

        movie = new ComputeMovie();
        movie.classType(classType);
        movie.setNoTickets(Integer.valueOf(tickets));
        movie.computeTotal();
        totalPrice = Double.toString(movie.getTotalPrice());

        String infoText = "Movie Title: " + title + "\n" +
                "Class Type: " + classType + "\n" + "Time: " + time + "\n" +
                "No of Tickets: " + tickets + "\n" + "Total Price: " + totalPrice;

        output.setText(infoText);

// movie.sendSMS();

        saveQuery();
//        dropQuery();
    }


    public void saveQuery () {
        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER, title VARCHAR, classType VARCHAR, " +
                    "time VARCHAR, price VARCHAR" + ")");
            db.execSQL("INSERT INTO history (id, title, classType, time, price) " +
                    "VALUES ('" + userID + "' , '" + title + "' , '" + classType + "' , '" + time + "' , '" +  totalPrice +"' )");

//            Toast.makeText(this, "History Saved", Toast.LENGTH_SHORT).show();

            Cursor c = db.rawQuery("SELECT * FROM history", null);

            int idIndex = c.getColumnIndex("id");
            int titleIndex = c.getColumnIndex("title");
            int classIndex = c.getColumnIndex("classType");
            int timeIndex = c.getColumnIndex("time");
            int priceIndex = c.getColumnIndex("price");

            c.moveToFirst();

            while(c != null) {
                Log.i("Output: ID", c.getString(idIndex));
                Log.i("Output: Title", c.getString(titleIndex));
                Log.i("Output: Class", c.getString(classIndex));
                Log.i("Output: Time", c.getString(timeIndex));
                Log.i("Output: Price", c.getString(priceIndex));
                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropQuery() {
        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER, title VARCHAR, classType VARCHAR, " +
                    "time VARCHAR, price VARCHAR" + ")");
            db.execSQL("DROP TABLE IF EXISTS history");
            Toast.makeText(this, "Drop Table", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(View view) {
        Intent intent = new Intent(OutputScreen.this, LoginScreen.class);
        startActivity(intent);
    }

}
