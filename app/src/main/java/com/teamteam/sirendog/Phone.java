package com.teamteam.sirendog;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Phone implements Serializable {
    String name;
    String phone;

    public Phone(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
