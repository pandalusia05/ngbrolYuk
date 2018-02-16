package com.ngobrolyuk.android.models;

/**
 * Created by MIP on 1/6/2018.
 */

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class User extends RealmObject {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;

    public int id;
    public boolean isAdmin;
}

