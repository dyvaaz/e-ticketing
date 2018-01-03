package com.diffa.eticket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryScreen extends AppCompatActivity {

    SQLiteDatabase db;
    TextView output;
    Bundle extras;

    Button btnBack;

    int userID;
    String userIDString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_screen);

        output = (TextView) findViewById(R.id.output);
        btnBack = (Button)findViewById(R.id.btnBack);
        extras = getIntent().getExtras();
        userIDString = extras.getString("userID");
        userID = Integer.parseInt(userIDString);

        saveQuery();
    }

    public void logout(View view) {
        Intent intent = new Intent(HistoryScreen.this, LoginScreen.class);
        startActivity(intent);
    }

    public void saveQuery() {
        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER, title VARCHAR, classType VARCHAR, " +
                    "time VARCHAR, price VARCHAR" + ")");

            Cursor c = db.rawQuery("SELECT * FROM history WHERE id = " + userID + "", null);

            int idIndex = c.getColumnIndex("id");
            int titleIndex = c.getColumnIndex("title");
            int classIndex = c.getColumnIndex("classType");
            int timeIndex = c.getColumnIndex("time");
            int priceIndex = c.getColumnIndex("price");

            c.moveToFirst();

            while (c != null) {
                Log.i("History: ID", c.getString(idIndex));
                Log.i("History: Title", c.getString(titleIndex));
                Log.i("History: Class", c.getString(classIndex));
                Log.i("History: Time", c.getString(timeIndex));
                Log.i("History: Price", c.getString(priceIndex));

                output.append("\n\nMovie Title: " + c.getString(titleIndex) + "\n" +
                        "Class Type: " + c.getString(classIndex) + "\n" + "Time: " + c.getString(timeIndex) + "\n" +
                        "Total Price: " + c.getString(priceIndex) + "\n\n" +
                        "----------------------------------------------"
                );

                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropQuery() {
        try {
            db = this.openOrCreateDatabase("AppDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER, title VARCHAR, classType VARCHAR, " +
                    "time VARCHAR, price VARCHAR" + ")");
            db.execSQL("DROP TABLE IF EXISTS history");
            Toast.makeText(this, "Drop Table", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
