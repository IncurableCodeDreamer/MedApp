package com.example.klaudia.medicalcenter.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Klaudia on 11.03.2018.
 */

public class Results {
    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("place_id")
    @Expose
    private String place_id;

    @SerializedName("reviews")
    @Expose
    private Reviews[] reviews;

    @SerializedName("scope")
    @Expose
    private String scope;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("international_phone_number")
    @Expose
    private String international_phone_number;

    @SerializedName("adr_address")
    @Expose
    private String adr_address;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("reference")
    @Expose
    private String reference;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("opening_hours")
    @Expose
    private OpeningHours opening_hours;

    @SerializedName("utc_offset")
    @Expose
    private String utc_offset;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("photos")
    @Expose
    private Photos[] photos;

    @SerializedName("vincinity")
    @Expose
    private String vicinity;

    @SerializedName("address_components")
    @Expose
    private AddressComponent[] address_components;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("formatted_address")
    @Expose
    private String formatted_address;

    @SerializedName("formatted_phone_number")
    @Expose
    private String formatted_phone_number;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("types")
    @Expose
    private String[] types;

    public AddressComponent[] getAddressComponents() {
        return address_components;
    }

    public void setAddressComponents(AddressComponent[] address_components) {
        this.address_components = address_components;
    }

    public String getAdrAddress() {
        return adr_address;
    }

    public void setAdrAddress(String adrAddress) {
        this.adr_address = adrAddress;
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formatted_address = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formatted_phone_number;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formatted_phone_number = formattedPhoneNumber;
    }

    public String getInternationalPhoneNumber() {
        return international_phone_number;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.international_phone_number = internationalPhoneNumber;
    }

    public Reviews[] getReviews() {
        return reviews;
    }

    public void setReviews(Reviews[] reviews) {
        this.reviews = reviews;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUtcOffset() {
        return utc_offset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utc_offset = utcOffset;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Photos[] getPhotos ()
    {
        return photos;
    }

    public void setPhotos (Photos[] photos)
    {
        this.photos = photos;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPlace_id ()
    {
        return place_id;
    }

    public void setPlace_id (String place_id)
    {
        this.place_id = place_id;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getVicinity ()
    {
        return vicinity;
    }

    public void setVicinity (String vicinity)
    {
        this.vicinity = vicinity;
    }

    public String getScope ()
    {
        return scope;
    }

    public void setScope (String scope)
    {
        this.scope = scope;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String[] getTypes ()
    {
        return types;
    }

    public void setTypes (String[] types)
    {
        this.types = types;
    }

    public String getReference ()
    {
        return reference;
    }

    public void setReference (String reference)
    {
        this.reference = reference;
    }

    public OpeningHours getOpening_hours ()
    {
        return opening_hours;
    }

    public void setOpening_hours (OpeningHours opening_hours)
    {
        this.opening_hours = opening_hours;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photos = "+photos+", id = "+id+", place_id = "+place_id+", icon = "+icon+", vicinity = "+vicinity+", scope = "+scope+", name = "+name+", rating = "+rating+", types = "+types+", reference = "+reference+", opening_hours = "+opening_hours+", geometry = "+geometry+"]";
    }
}
