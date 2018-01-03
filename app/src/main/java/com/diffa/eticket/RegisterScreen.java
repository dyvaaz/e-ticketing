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

public class RegisterScreen extends AppCompatActivity {

    Button btnBack, btnRegisterUser;

    EditText usernameInput, passwordInput, repeatPassInput, fullnameInput, phoneNumberInput;

    String username, password, repeatPass, fullname, phoneNumber;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnRegisterUser = (Button)findViewById(R.id.btnRegisterUser);

        usernameInput = (EditText)findViewById(R.id.username);
        fullnameInput = (EditText)findViewById(R.id.fullname);
        phoneNumberInput = (EditText)findViewById(R.id.phoneNumber);
        passwordInput = (EditText)findViewById(R.id.password);
        repeatPassInput = (EditText)findViewById(R.id.repeatPassword);

    }

    public void BackIntent(View view) {
        Intent Login = new Intent(RegisterScreen.this, LoginScreen.class);
        startActivity(Login);
    }

    public void RegisterUser(View view) {

        checkInputs();
    }

    public void checkInputs() {
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        repeatPass = repeatPassInput.getText().toString();
        fullname = fullnameInput.getText().toString();
        phoneNumber = phoneNumberInput.getText().toString();

        if(username.equals("")) {
            Toast.makeText(this, "Username is Empty", Toast.LENGTH_SHORT).show();
        } else if (fullname.equals("")) {
            Toast.makeText(this, "Fullname is Empty", Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.equals("")) {
            Toast.makeText(this, "Phone Number is Empty", Toast.LENGTH_SHORT).show();
        } else if(password.equals("")) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
        } else if (repeatPass.equals("")) {
            Toast.makeText(this, "Repeat Password is Empty", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(repeatPass)) {
            Toast.makeText(this, "Incorrect Repeat Password", Toast.LENGTH_SHORT).show();
        } else {
            registerQuery();
            Intent Login = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(Login);
        }
    }

    public void registerQuery () {
        try {
            db = this.openOrCreateDatabase("clientDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username VARCHAR, password VARCHAR, " +
                    "fullname VARCHAR, phone VARCHAR" + ")");
            db.execSQL("INSERT INTO users (username, password, fullname, phone) " +
                    "VALUES ('" + username + "' , '" + password + "' , '" + fullname + "' , '" +  phoneNumber +"' )");

            Toast.makeText(this, "Registration Complete", Toast.LENGTH_SHORT).show();

            Cursor c = db.rawQuery("SELECT * FROM users", null);

            int idIndex = c.getColumnIndex("id");
            int userIndex = c.getColumnIndex("username");
            int passIndex = c.getColumnIndex("password");
            int nameIndex = c.getColumnIndex("fullname");
            int phoneIndex = c.getColumnIndex("phone");

            c.moveToFirst();

            while(c != null) {
                Log.i("Users: ID", c.getString(idIndex));
                Log.i("Users: User", c.getString(userIndex));
                Log.i("Users: Name", c.getString(nameIndex));
                Log.i("Users: Phone", c.getString(phoneIndex));
                Log.i("Users: Pass", c.getString(passIndex));
                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
