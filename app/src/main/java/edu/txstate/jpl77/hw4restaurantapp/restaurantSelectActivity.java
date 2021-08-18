package edu.txstate.jpl77.hw4restaurantapp;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class restaurantSelectActivity extends ListActivity {

    List<restaurant> restaurantList = new ArrayList<restaurant>();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        restaurant selectedRestaurant = restaurantList.get(position);

        DecimalFormat f = new DecimalFormat("###,###.##");
        Toast.makeText(restaurantSelectActivity.this, "Cost per Person is: $" +
                f.format(selectedRestaurant.getCostPerPerson()), Toast.LENGTH_SHORT).show();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(restaurantSelectActivity.this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("KeyID", selectedRestaurant.getId());
        editor.putString("KeyCity", selectedRestaurant.getCity());
        editor.putString("KeyName", selectedRestaurant.getName());
        editor.putString("KeyPhoneNumber", selectedRestaurant.getPhoneNum());
        editor.putFloat("KeyCost", (float) selectedRestaurant.getCostPerPerson());
        editor.putString("KeyURL", selectedRestaurant.getUrl());
        editor.putInt("KeyPosition", position);

        editor.commit();

        startActivity(new Intent(restaurantSelectActivity.this, calculateActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Accept", "application/json"));
        restClient.get(restaurantSelectActivity.this, "notes.json", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        //super.onSuccess(statusCode, headers, response);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                restaurant bean = new restaurant(response.getJSONObject(i));
                                restaurantList.add(bean);
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                        setListAdapter(new ArrayAdapter<restaurant>(restaurantSelectActivity.this, R.layout.activity_restaurant_select, R.id.txtLayout, restaurantList));
                    }
                });
    }
}
