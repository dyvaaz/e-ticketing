package com.diffa.eticket;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText usernameInput, passwordInput;
    String username, password;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        usernameInput = (EditText)findViewById(R.id.usernameInput);
        passwordInput = (EditText)findViewById(R.id.passwordInput);

    }
    
    public void LoginIntent(View view) {
        checkInputsAndQuery();
    }

    public void RegisterIntent(View view) {
        Intent register = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(register);

    }

    public void loginQuery() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        Boolean isFound = false;

        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);

            Cursor c = db.rawQuery("SELECT * FROM users WHERE username = " + "'" + username + "'", null);

            c.moveToFirst();

            int idIndex = c.getColumnIndex("id");
            int userIndex = c.getColumnIndex("username");
            int passIndex = c.getColumnIndex("password");
            int nameIndex = c.getColumnIndex("fullname");

            while(!isFound) {
                String dbId = c.getString(idIndex);
                String dbUser = c.getString(userIndex);
                String dbPass = c.getString(passIndex);
                String dbName = c.getString(nameIndex);

                if(password.equals(dbPass)) {
                    Intent Main = new Intent(LoginScreen.this, MainScreen.class);
                    Main.putExtra("userID", dbId);
                    startActivity(Main);
                    Toast.makeText(this, "Welcome back " + dbName + "!", Toast.LENGTH_LONG).show();
                    isFound = true;
                } else {
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    c.moveToNext();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInputsAndQuery() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();

        if(username.equals("")) {
            Toast.makeText(this, "Username is Empty", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
        } else {
            loginQuery();
        }
    }

    public void userLists() {
        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username VARCHAR, password VARCHAR, " +
                    "fullname VARCHAR, phone VARCHAR" + ")");

            Cursor c = db.rawQuery("SELECT * FROM users", null);
            int idIndex = c.getColumnIndex("id");
            int userIndex = c.getColumnIndex("username");
            int passIndex = c.getColumnIndex("password");
            int nameIndex = c.getColumnIndex("fullname");
            int phoneIndex = c.getColumnIndex("phone");

            c.moveToFirst();

            while(c != null) {
                Log.i("LUsers: ID", c.getString(idIndex));
                Log.i("LUsers: User", c.getString(userIndex));
                Log.i("LUsers: Name", c.getString(nameIndex));
                Log.i("LUsers: Phone", c.getString(phoneIndex));
                Log.i("LUsers: Pass", c.getString(passIndex));
                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
