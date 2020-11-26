package com.teamteam.sirendog;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

public class SafetyMapRequest {
    @SerializedName("esntlId")
    public String esntlId;
    @SerializedName("authKey")
    public  String authKey;
    public Integer pageIndex = 1 ;
    public Integer pageUnit = 100;
    public Double minY = 0d;
    public Double minX = 0d;
    public Double maxY = 0d;
    public Double maxX = 0d;
    public String[] detailDate1 = {"09"};
    public String xmlUseYN = "N";

    SafetyMapRequest(Context context){
        this(context,1);
    }

    SafetyMapRequest(Context context, int pageIndex)
    {
        this.esntlId = (context.getResources().getString(R.string.api_id));
        this.authKey = context.getResources().getString(R.string.api_key);
        this.pageIndex= pageIndex;
    }
}
