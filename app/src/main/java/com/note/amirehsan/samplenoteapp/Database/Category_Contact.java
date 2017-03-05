package com.note.amirehsan.samplenoteapp.Database;

/**
 * Created by AMiR ehsan on 2/26/2017.
 */

public class Category_Contact {

    //private variables
    int _id;
    String _name;
    String _date;
    String _color;

    // Empty constructor
    public Category_Contact() {

    }

    // constructor
    public Category_Contact(int id, String name, String date, String color) {
        this._id = id;
        this._name = name;
        this._date = date;
        this._color = color;
    }

    // constructor
    public Category_Contact(String name, String date, String color) {
        this._name = name;
        this._date = date;
        this._color = color;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }


    // getting date
    public String getDate() {
        return this._date;
    }

    // setting date
    public void setDate(String date) {
        this._date = date;
    }

    // getting color
    public String getColor() {
        return this._color;
    }

    // setting color
    public void setColor(String color) {
        this._color = color;
    }


}