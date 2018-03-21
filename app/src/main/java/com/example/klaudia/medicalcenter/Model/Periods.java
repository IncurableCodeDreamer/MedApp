package com.example.klaudia.medicalcenter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Klaudia on 13.03.2018.
 */

public class Periods {

    @SerializedName("open")
    @Expose
    private Open open;

    @SerializedName("close")
    @Expose
    private Close close;

    public Open getOpen ()
    {
        return open;
    }

    public void setOpen (Open open)
    {
        this.open = open;
    }

    public Close getClose ()
    {
        return close;
    }

    public void setClose (Close close)
    {
        this.close = close;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [open = "+open+", close = "+close+"]";
    }
}
