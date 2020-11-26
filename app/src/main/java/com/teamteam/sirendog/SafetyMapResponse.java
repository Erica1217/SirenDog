package com.teamteam.sirendog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SafetyMapResponse {

    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("list")
    @Expose
    private List<SafetyMapData> list = null;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<SafetyMapData> getList() {
        return list;
    }

    public void setList(List<SafetyMapData> list) {
        this.list = list;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
