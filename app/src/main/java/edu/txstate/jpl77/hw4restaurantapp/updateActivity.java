package edu.txstate.jpl77.hw4restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class updateActivity extends AppCompatActivity {

    int intIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView restaurantID = findViewById(R.id.txtID);
        TextView restaurantPhone = findViewById(R.id.txtPhone);
        TextView restaurantName = findViewById(R.id.txtName);
        TextView restaurantCity = findViewById(R.id.txtCity);
        TextView restaurantCost = findViewById(R.id.txtCost);
        final EditText numberOfPeople = findViewById(R.id.txtNumberOfPeople);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(updateActivity.this);

        final restaurant selectedRestaurant = new restaurant(pref.getInt("KeyID", 0),
                pref.getString("KeyCity", null),
                pref.getString("KeyName", null),
                pref.getString("KeyPhoneNum", null),
                pref.getString("KeyURL", null),
                pref.getFloat("KeyCost", 0));
                

        intIndex = pref.getInt("KeyPosition", 0);

        DecimalFormat f = new DecimalFormat("###,###.##");

        restaurantID.setText("ID: " + String.valueOf(selectedRestaurant.getId()));
        restaurantCity.setText("Brand: " + selectedRestaurant.getCity());
        restaurantName.setText("Name: " + selectedRestaurant.getName());

        Button update = findViewById(R.id.btnInfo);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNewCost = restaurantCost.getText().toString();
                StringEntity entity = null;

                try{
                    entity = new StringEntity(strNewCost);
                    entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
                    String url = "restaurant/" + (intIndex + 1) + "/costPerPerson.json";
                    restClient.put(updateActivity.this, url, entity, "application/text", new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(updateActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            Toast.makeText(updateActivity.this, "Success", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button home = findViewById(R.id.btnStart);

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(updateActivity.this, MainActivity.class));
            }
        });

    }
}