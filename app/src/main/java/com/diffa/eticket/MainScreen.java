package com.diffa.eticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainScreen extends AppCompatActivity {

    Spinner menuSpinner;
    TextView titleText, sypnosisText;
    ImageView poster;
    RadioGroup classRadio, timeRadio;
    RadioButton time1, time2, time3, time4, regularClass, deluxeClass, classSelected, timeSelected;
    Button btnBuy, logout;
    EditText noTickets;
    String title, tickets;

    Bundle extras;

    int positionSpinner = 0;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        sypnosisText = (TextView)findViewById(R.id.sypnosisText);
        titleText = (TextView)findViewById(R.id.titleText);
        poster = (ImageView)findViewById(R.id.poster);
        classRadio = (RadioGroup) findViewById(R.id.classRadio);
        timeRadio = (RadioGroup) findViewById(R.id.timeRadio);
        btnBuy = (Button) findViewById(R.id.btnBuy);
        noTickets = (EditText) findViewById(R.id.noTickets);

        extras = getIntent().getExtras();
        userID = extras.getString("userID");
        String test = extras.getString("test");
        renderMenu();


    }

    public void renderMenu() {
        menuSpinner = (Spinner) findViewById(R.id.menuSpinner);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.menu_arrays, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        menuSpinner.setAdapter(staticAdapter);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSpinner = position;
                title = getResources().getStringArray(R.array.menu_arrays)[positionSpinner];
                posterRender(positionSpinner);
                String content = getResources().getStringArray(R.array.content_arrays)[positionSpinner];
                titleText.setText(title);
                sypnosisText.setText(content);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void buyTicket(View view) {

        int selectedClass = classRadio.getCheckedRadioButtonId();
        int selectedTime = timeRadio.getCheckedRadioButtonId();

        classSelected = (RadioButton) findViewById(selectedClass);
        timeSelected = (RadioButton) findViewById(selectedTime);

        tickets = noTickets.getText().toString();

//        Toast.makeText(getApplicationContext(), userID + " " + title + " " + classSelected.getText()+ " " + timeSelected.getText() + " " + tickets, Toast.LENGTH_SHORT).show();
        Intent output = new Intent(MainScreen.this, OutputScreen.class);
        output.putExtra("title", title);
        output.putExtra("class", classSelected.getText());
        output.putExtra("time", timeSelected.getText());
        output.putExtra("tickets", tickets);
        output.putExtra("position", positionSpinner);
        output.putExtra("userID", userID);
        startActivity(output);
    }

    public void clickHistory(View view) {
        Intent History = new Intent(MainScreen.this, HistoryScreen.class);
        History.putExtra("userID", userID);
        startActivity(History);
    }

    public void logoutClick(View view) {
        Intent Login = new Intent(MainScreen.this, LoginScreen.class);
        startActivity(Login);
    }

    public void posterRender(int position) {
        switch(position) {
            case 0:
                poster.setImageResource(R.drawable.firstavenger);
                break;
            case 1:
                poster.setImageResource(R.drawable.avengers);
                break;
            case 2:
                poster.setImageResource(R.drawable.wintersoldier);
                break;
            case 3:
                poster.setImageResource(R.drawable.ultron);
                break;
            default:
                poster.setImageResource(R.drawable.civilwar);
                break;
        }
    }

}
