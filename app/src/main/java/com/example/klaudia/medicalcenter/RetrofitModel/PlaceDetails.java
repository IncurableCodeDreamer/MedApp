package com.example.klaudia.medicalcenter.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Klaudia on 13.03.2018.
 */

public class PlaceDetails {

    @SerializedName("result")
    @Expose
    private Results result;

    @SerializedName("html_attributions")
    @Expose
    private String[] html_attributions;

    @SerializedName("status")
    @Expose
    private String status;

    public Results getResult ()
    {
        return result;
    }

    public void setResult (Results result)
    {
        this.result = result;
    }

    public String[] getHtml_attributions ()
    {
        return html_attributions;
    }

    public void setHtml_attributions (String[] html_attributions)
    {
        this.html_attributions = html_attributions;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", html_attributions = "+html_attributions+", status = "+status+"]";
    }
}
