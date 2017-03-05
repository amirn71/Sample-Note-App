package com.note.amirehsan.samplenoteapp.Database;

/**
 * Created by AMiR ehsan on 2/26/2017.
 */

public class Note_Contact {

    //private variables
    int _id;
    String _name;
    String _category;
    String _data;
    String _date;
    String _color;
    String _draw;

    // Empty constructor
    public Note_Contact() {

    }

    // constructor
    public Note_Contact(int id, String name, String category, String data, String date, String color, String draw) {
        this._id = id;
        this._name = name;
        this._category = category;
        this._data = data;
        this._date = date;
        this._color = color;
        this._draw = draw;
    }

    // constructor
    public Note_Contact(String name, String category, String data, String date, String color, String draw) {
        this._name = name;
        this._category = category;
        this._data = data;
        this._date = date;
        this._color = color;
        this._draw = draw;
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

    // getting category
    public String getCategory() {
        return this._category;
    }

    // setting category
    public void setCategory(String category) {
        this._category = category;
    }

    // getting data
    public String getData() {
        return this._data;
    }

    // setting data
    public void setData(String data) {
        this._data = data;
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

    // getting color
    public String getDraw() {
        return this._draw;
    }

    // setting color
    public void setDraw(String draw) {
        this._draw = draw;
    }

}