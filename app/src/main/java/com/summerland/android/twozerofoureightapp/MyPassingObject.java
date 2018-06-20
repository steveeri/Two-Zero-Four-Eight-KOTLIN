package com.summerland.android.twozerofoureightapp;

import java.io.Serializable;

/**
 * Created by steve on 13/10/16.
 */
public class MyPassingObject implements Serializable {

    private String myStrName = "Bob";

    @Override
    public String toString() {
        return super.toString() + "myStrName = " + myStrName;
    }

    public String getMyStrName() {
        return this.myStrName;
    }
}
