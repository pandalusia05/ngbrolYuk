package com.ngobrolyuk.android.models;

/**
 * Created by MIP on 1/6/2018.
 */

import com.google.gson.annotations.SerializedName;


public class MainResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}
