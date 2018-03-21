package com.example.klaudia.medicalcenter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Klaudia on 11.03.2018.
 */

public class OpeningHours {

    @SerializedName("periods")
    @Expose
    private Periods[] periods;

    @SerializedName("open_now")
    @Expose
    private String open_now;

    @SerializedName("weekday_text")
    @Expose
    private String [] weekday_text;


    public Periods[] getPeriods ()
    {
        return periods;
    }

    public void setPeriods (Periods[] periods)
    {
        this.periods = periods;
    }

    public String getOpen_now ()
    {
        return open_now;
    }

    public void setOpen_now (String open_now)
    {
        this.open_now = open_now;
    }

    public String[] getWeekday_text ()
    {
        return weekday_text;
    }

    public void setWeekday_text (String[] weekday_text)
    {
        this.weekday_text = weekday_text;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [periods = "+periods+", open_now = "+open_now+", weekday_text = "+weekday_text+"]";
    }
}
