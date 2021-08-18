package edu.txstate.jpl77.hw4restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class calculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        //setting restaurant info into text views
        TextView restID = findViewById(R.id.txtID);
        TextView restName = findViewById(R.id.txtName);
        TextView restCity = findViewById(R.id.txtCity);
        TextView restPhone = findViewById(R.id.txtPhone);
        TextView restCost = findViewById(R.id.txtCost);
        final EditText numberOfPeople = findViewById(R.id.txtNumberOfPeople);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(calculateActivity.this);

        final restaurant selectedRestaurant = new restaurant(pref.getInt("KeyID", 0),
                pref.getString("KeyName", null),
                pref.getString("KeyCity", null),
                pref.getString("KeyPhoneNum", null),
                pref.getString("KeyUrl", null),
                pref.getFloat("KeyCost", 0));

        DecimalFormat df = new DecimalFormat("###,###.##");

        restID.setText("id: " + String.valueOf(selectedRestaurant.getId()));
        restName.setText("name: " + selectedRestaurant.getName());
        restCity.setText("city: " + selectedRestaurant.getCity());
        restPhone.setText("phoneNum: " + selectedRestaurant.getPhoneNum());
        restCost.setText("costPerPerson: $" + df.format(selectedRestaurant.getCostPerPerson()));

        Button getInfo = findViewById(R.id.btnInfo);
        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(selectedRestaurant.getUrl())));
            }
        });


        //Total catering cost
        Button totalCost = findViewById(R.id.calculateTotalBtn);

        totalCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int intNumPpl = Integer.parseInt(numberOfPeople.getText().toString());
                    double totalCatering = selectedRestaurant.getCostPerPerson() * intNumPpl;
                    Toast.makeText(calculateActivity.this, "Total Catering Cost: $" + totalCatering, Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){
                    Toast.makeText(calculateActivity.this, "Please enter a whole number for the number of people", Toast.LENGTH_LONG).show();

                }
            };

        });
    }}