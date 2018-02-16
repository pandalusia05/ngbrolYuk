package com.ngobrolyuk.android.models;

/**
 * Created by MIP on 1/6/2018.
 */

import com.google.gson.annotations.SerializedName;


public class ChatRoom {

    @SerializedName("id")
    public String id;
    @SerializedName("room_name")
    public String room_name;
    @SerializedName("room_desc")
    public String room_desc;
}

