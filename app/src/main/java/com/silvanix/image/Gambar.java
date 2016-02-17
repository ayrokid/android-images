package com.silvanix.image;

/**
 * Created by ayrokid on 17/02/16.
 */
public class Gambar {

    // private variable
    int _id;
    String _name;
    byte[] _image;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
}
