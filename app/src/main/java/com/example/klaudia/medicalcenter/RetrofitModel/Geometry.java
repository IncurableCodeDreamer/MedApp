package com.example.klaudia.medicalcenter.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Klaudia on 11.03.2018.
 */

public class Geometry {

    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    @SerializedName("location")
    @Expose
    private Location location;

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ClassPojo [viewport = " + viewport + ", location = " + location + "]";
    }
}
