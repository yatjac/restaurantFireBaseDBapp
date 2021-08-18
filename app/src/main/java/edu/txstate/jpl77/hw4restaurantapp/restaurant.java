package edu.txstate.jpl77.hw4restaurantapp;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class restaurant {
    private int id;
    private String name;
    private String city;
    private String url;
    private String phoneNum;
    private double costPerPerson;

    public restaurant(int id, String name, String city, String phoneNum, String url, double cost) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.phoneNum = phoneNum;
        this.url = url;
        this.costPerPerson = cost;
    }
    public restaurant() {
    }

    public restaurant(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.name = object.getString("name");
            this.city = object.getString("city");
            this.phoneNum = object.getString("phoneNumber");
            this.url = object.getString("url");
            this.costPerPerson = object.getDouble("costPerPerson");
        }
        catch (JSONException e){
        e.printStackTrace();
    }


    }


    @NonNull
    @Override
    public String toString() {
        return id + ": " + city + " " + name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(double costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public String getPhoneNum() {
        return url;
    }

    public void setPhoneNum(String phoneNum) {
        this.url = phoneNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
